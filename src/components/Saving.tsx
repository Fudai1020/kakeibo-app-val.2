type props = {
  onAddClick:() => void;
  setModalType:React.Dispatch<React.SetStateAction<"transaction" | "saving" | null>>;
  onBalanceChange:(balance:number) => void;
  selectedDate:Date;
  sharedWith:string|null;
}
import { useEffect, useState } from 'react';
import '../styles/saving.css' 
import AnimateNumber from './AnimateNumber';

const Saving = ({onAddClick,setModalType,onBalanceChange,selectedDate,sharedWith}:props) => {
  const [savingTotal,setSavingTotal] = useState(0);
  const [savingAllocations,setSavingAllocations] = useState<{name:string,amount:number}[]>([]);//オブジェクトの型を指定してstate管理

  //ユーザ情報と共有相手の情報をselectedDate、sharedwithマウント時に取得
 
//初回マウント時にデータを取得

//savingAllocationモーダルを開く情報を親に渡す
const handleClick = ()=>{
  setModalType("saving");
  onAddClick();
}
  return (
    <div className="saving-box">
        <h1 style={{marginBottom:'-20px'}}>今月の収支</h1>
        <h2 style={{marginBottom:'-20px'}}><AnimateNumber value={savingTotal} /></h2>
        <h2 style={{marginBottom:'-5px'}}> 貯金一覧</h2>
        <div className='saving-category'>
          {savingAllocations.map((item,index)=>(
            <p key={index}>
              {item.name}：¥{item.amount.toLocaleString()}
            </p>
          ))}
        </div>
        <button className='Saving-button' onClick={handleClick}>振り分け</button>       
    </div>
  )
}

export default Saving