//プロップスで渡される型定義
type Props = {
  onAddClick: () => void; //何も返さない型
  setModalType: React.Dispatch<React.SetStateAction<"transaction" | "saving" | null>>;//子コンポーネントはから親の状態を変更できるようにする
  selectedDate: Date;
  sharedWith: string | null; 
  refreshTrigger?:Number;
};

import { useEffect, useState } from 'react';
import '../styles/income.css'
import AnimateNumber from './AnimateNumber';

const Income = ({ onAddClick, setModalType, selectedDate, sharedWith ,refreshTrigger}: Props) => {
  const [total, setTotal] = useState(0);

  const fetchIncome = async()=>{
  const year = selectedDate.getFullYear();
  const month = selectedDate.getMonth()+1;
  const userId = localStorage.getItem('userId');
 
 if(!userId) return;

 fetch(`http://localhost:8080/api/incomes/${userId}/${year}/${month}/sum`)
  .then(res => res.json())
  .then(data => {
    setTotal(data ?? 0);
  })
  .catch(err => {
    console.error('取得に失敗しました',err);
  });
}
useEffect(()=>{
fetchIncome();
},[selectedDate,refreshTrigger])

 

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

export default Income;
