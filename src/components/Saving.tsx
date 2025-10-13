type props = {
  onAddClick:() => void;
  setModalType:React.Dispatch<React.SetStateAction<"transaction" | "saving" | null>>;
  selectedDate:Date;
  sharedWith:string|null;
  refreshTrigger:number;
  partnerData:any[];  
}
import { useEffect, useState } from 'react';
import '../styles/saving.css' 
import AnimateNumber from './AnimateNumber';

const Saving = ({onAddClick,setModalType,selectedDate,sharedWith,refreshTrigger,partnerData}:props) => {
  const [savingTotal,setSavingTotal] = useState(0);
  const [savingAllocations,setSavingAllocations] = useState<{id:number,name:string,totalAmount:number}[]>([]);
  const [margedAllocations,setMargedAllocations] = useState<{id:number,name:string,totalAmount:number}[]>([]);
  
  
  //ユーザ情報と共有相手の情報をselectedDate、sharedwithマウント時に取得
 useEffect(()=>{
  const userId = localStorage.getItem('userId');
  if(!userId) return;

  const year = selectedDate.getFullYear();
  const month = selectedDate.getMonth()+1;

  const incomeUrl = `http://localhost:8080/api/incomes/${userId}/${year}/${month}/sum`;
  const paymentUrl = `http://localhost:8080/api/payments/${userId}/${year}/${month}/sum`;
  const savingUrl = `http://localhost:8080/api/savings/${userId}/${year}/${month}/cumulative`;
  (async()=>{
    try{
      setSavingAllocations([]);
      const [resIncome,resPayment,resSummary] = await Promise.all([
        fetch(incomeUrl),
        fetch(paymentUrl),
        fetch(savingUrl),
      ]);
      const txtIncome = await resIncome.text();
      const txtPayment = await resPayment.text();
      const summaries = await resSummary.json();
      const incomeValue = txtIncome ? JSON.parse(txtIncome) : 0;
      const paymentValue = txtPayment ? JSON.parse(txtPayment) : 0;
      setSavingTotal(incomeValue - paymentValue);
      setSavingAllocations(summaries);
    }catch(err){
      console.error('データ取得失敗',err);
    }
  })();
 },[selectedDate,refreshTrigger])
useEffect(()=>{

  let marged = [...savingAllocations];
  if(sharedWith || partnerData?.length) {
  partnerData.forEach((p) => {
    const existing = marged.find((m) => m.name === p.name);
    if(existing){
      existing.totalAmount += p.totalAmount;
    }else{
      marged.push(p);
    }
  })
}
  setMargedAllocations(marged);
},[sharedWith,partnerData,savingAllocations]);
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
          {margedAllocations.map((item,index)=>(
            <p key={index}>
              {item.name}：¥{item.totalAmount.toLocaleString()}
            </p>
          ))}
        </div>
        <button className='Saving-button' onClick={handleClick}>振り分け</button>       
    </div>
  )
}

export default Saving