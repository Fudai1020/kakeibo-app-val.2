import { useEffect, useState } from 'react';
import '../styles/payment.css';
import Charts from './Charts';
import AnimateNumber from './AnimateNumber';
import type { PaymentResponse } from '../types/types';
import { apiFetch } from '../utils/api';

type Props = {
  onAddClick: () => void;
  setModalType: React.Dispatch<React.SetStateAction<"transaction" | "saving" | null>>;
  selectedDate: Date;
  sharedWith: string | null;
  partnerName:string|null;
  refreshTrigger?:Number;
  partnerData:PaymentResponse[];
};

const Payment = ({ onAddClick, setModalType, selectedDate, sharedWith,partnerName,refreshTrigger,partnerData }: Props) => {
  const [totalAmount, setTotalAmount] = useState(0);
  //オブジェクトの型を指定してstate管理
  const [categoryTotals, setCategoryTotals] = useState<{
    [key: string]: { amount: number; isMine: boolean }[];
  }>({});
  const [privateState,setPrivateState] = useState<{[key:string]:boolean}>({});

  //selectedDate,SharedWithのマウント時に自分と相手の情報を取得
  useEffect(()=>{
    const userId = localStorage.getItem('userId');
    const year = selectedDate.getFullYear();
    const month = selectedDate.getMonth()+1;
    if(!userId) return;

    apiFetch(`${import.meta.env.VITE_API_URL}/api/payments/${userId}/${year}/${month}/summary`)
    .then(res => res.json())
  .then(data => {
    setTotalAmount(data.totalAmount);
    const categoryMap:{ [key:string]: {amount:number; isMine:boolean }[] } = {};
    (data.categoryTotals ?? []).forEach((c:{ category:string; total:number }) =>{
      categoryMap[c.category] = [{  amount:c.total,isMine:true}];
    });
    partnerData.forEach((p) =>{
      const category = p.paymentCategoryName ?? "未分類";
      if(!categoryMap[category]) categoryMap[category] = [];
      categoryMap[category].push({amount:Number(p.amount),isMine:false});
    });
    setCategoryTotals(categoryMap);
    setTotalAmount(Object.values(categoryMap).flat().reduce((sum,item)=>sum+item.amount,0));
  })
  .catch(err => console.error('支出取得失敗',err));
  },[selectedDate,sharedWith,refreshTrigger,partnerData]);
  
  const handleClick = () => {
    setModalType("transaction");  
    onAddClick();
  };
  //チェックボックスで公開、非公開を切り替え
  const handleCheck = async(categoryName:string)=>{

  }

  return (
    <div className='payment-box'>
      <h1 style={{ marginBottom: '-15px' }}>今月の支出</h1>
      <h2 style={{ marginBottom: '-10px' }}><AnimateNumber value={totalAmount} /></h2>
      <div className='container'>
        <div className='left'>
          <div className='centered'>
            <div className='category-box'>
              <h2>項目</h2>
              <ul>
                {Object.entries(categoryTotals).map(([category, items]) => {  //オブジェクトを配列に変換してmap
                  const total = items.reduce((sum, item) => sum + item.amount, 0);
                  const isMine = items.every(item => item.isMine);  //自分のデータがitemのデータと一致しているか判定

                  return (
                    <li key={category} style={{ color: isMine ? '#f8f8ff' : '#ff0000ff' , listStyle:sharedWith &&   isMine? 'none':'disc'}}>
                      {sharedWith && isMine && <input type='checkbox' style={{scale:'1.5',marginRight:10,}}
                      checked={privateState[category]??false}
                       onChange={() => handleCheck(category)} />}
                      {category}・・・<AnimateNumber value={total} />{!isMine && partnerName ? `(${partnerName})`:''}
                    </li>
                  );
                })}
              </ul>
            </div>
          </div>
        </div>
        <div className='right'>
          <div className='chart'>
            <Charts paymentData={Object.entries(categoryTotals).map(([category, items]) => ({
              category,
              amount: items.reduce((sum, item) => sum + item.amount, 0),
            }))} />
          </div>
        </div>
      </div>
      <div className='button-layout'>
        <button onClick={handleClick}>支出入力</button>
      </div>
    </div>
  );
};

export default Payment;
