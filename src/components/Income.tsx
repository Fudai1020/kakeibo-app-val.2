//プロップスで渡される型定義
type Props = {
  onAddClick: () => void; //何も返さない型
  setModalType: React.Dispatch<React.SetStateAction<"transaction" | "saving" | null>>;//子コンポーネントはから親の状態を変更できるようにする
  selectedDate: Date;
  sharedWith: string | null; 
};

import { useEffect, useState } from 'react';
import '../styles/income.css'
import AnimateNumber from './AnimateNumber';

const Income = ({ onAddClick, setModalType, selectedDate, sharedWith }: Props) => {
  const [total, setTotal] = useState(0);

 

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
