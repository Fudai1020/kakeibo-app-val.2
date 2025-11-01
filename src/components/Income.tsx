//プロップスで渡される型定義
type Props = {
  onAddClick: () => void; //何も返さない型
  setModalType: React.Dispatch<React.SetStateAction<"transaction" | "saving" | null>>;//子コンポーネントはから親の状態を変更できるようにする
  selectedDate: Date;
  sharedWith: string | null; 
  refreshTrigger?:Number;
  partnerIncome:any;
};

import { useEffect, useState } from 'react';
import '../styles/income.css'
import AnimateNumber from './AnimateNumber';
import { apiFetch } from '../utils/api';

const Income = ({ onAddClick, setModalType, selectedDate, sharedWith ,refreshTrigger,partnerIncome}: Props) => {
  const [total, setTotal] = useState(0);

const fetchIncome = async()=>{
  const year = selectedDate.getFullYear();
  const month = selectedDate.getMonth()+1;
  const userId = localStorage.getItem('userId');
 
 if(!userId) return;
try{
 const res = await apiFetch(`http://localhost:8080/api/incomes/${userId}/${year}/${month}/sum`)
 if(!res) throw new Error("failed fetch");
 const myData = await res.json();
 const myValue = myData ? JSON.parse(myData):0;
 const partnerValue = sharedWith && partnerIncome ? Number(partnerIncome):0;
 setTotal(myValue + partnerValue);
}catch(err){
 console.error("取得に失敗しました",err);
}
}
useEffect(()=>{
fetchIncome();
},[selectedDate,refreshTrigger,sharedWith,partnerIncome])

 

//transactionモーダルを開く処理
  const handleClick = () => {
    setModalType("transaction");
    onAddClick();
  };

  return (
    <div className="income-box">
      <h1>今月の収入</h1>
      <h2><AnimateNumber value={total} /></h2>
      <button onClick={handleClick}>収入を追加</button>
    </div>
  );
};
export default Income
