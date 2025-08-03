import { useEffect, useState } from 'react';
import '../styles/payment.css';
import Charts from './Charts';
import AnimateNumber from './AnimateNumber';

type Props = {
  onAddClick: () => void;
  setModalType: React.Dispatch<React.SetStateAction<"transaction" | "saving" | null>>;
  selectedDate: Date;
  sharedWith: string | null;
  partnerName:string|null;
};

const Payment = ({ onAddClick, setModalType, selectedDate, sharedWith,partnerName }: Props) => {
  const [totalAmount, setTotalAmount] = useState(0);
  //オブジェクトの型を指定してstate管理
  const [categoryTotals, setCategoryTotals] = useState<{
    [key: string]: { amount: number; isMine: boolean }[];
  }>({});
  const [privateState,setPrivateState] = useState<{[key:string]:boolean}>({});

  //selectedDate,SharedWithのマウント時に自分と相手の情報を取得
  
//取得したデータを処理して月ごと、カテゴリ別に整形する
  const processPaymentData = (allData: any[], currentUid: string) => {
    //引数で渡ったデータに、作成日とisMineを追加する
    const filtered = allData
      .map((d) => {
        const createdAt = d.date?.toDate?.() || new Date();
        return {
          ...d,
          createdAt,
          isMine: !sharedWith || d.uid === currentUid,
        };
      })
      .filter(  //月ごとに絞り込む
        (d) =>
          d.createdAt.getFullYear() === selectedDate.getFullYear() &&
          d.createdAt.getMonth() === selectedDate.getMonth()
      );
      //絞り込んだデータの金額を合計する
    const total = filtered.reduce(
      (sum, d) => sum + (typeof d.amount === "number" ? d.amount : 0),0);
    setTotalAmount(total);
    //オブジェクトの形を指定して箱を用意
    const categoryMap: { [key: string]: { amount: number; isMine: boolean }[] } = {};
    filtered.forEach((item) => {
      const category = item.mainCategory ?? "未分類";   
      const amount = typeof item.amount === "number" ? item.amount : 0;
      if (!categoryMap[category]) categoryMap[category] = []; //categoryが存在しなければオブジェクトにcategoryを作成してpush
      categoryMap[category].push({ amount, isMine: item.isMine });
    });

    setCategoryTotals(categoryMap);
  };

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
                    <li key={category} style={{ color: isMine ? '#f8f8ff' : '#fff8dc' , listStyle:sharedWith &&   isMine? 'none':'disc'}}>
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
