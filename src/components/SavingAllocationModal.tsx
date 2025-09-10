import { useEffect, useMemo, useState } from 'react';
import '../styles/savingAllocationModal.css'
import { FaEdit, FaTrash } from 'react-icons/fa';
import AnimateNumber from './AnimateNumber';
//プロップスの方を指定する
type props = {
    onClose:() => void;
  selectedDate:Date;
  onSaveSuccess:()=>void;
}
const SavingAllocationModal = ({onClose,selectedDate,onSaveSuccess}:props) => {
  const [allocations,setAllocations] = useState<{name:string;amount:string}[]>([]); //データの値をオブジェクトとして管理
  const [allocationName,setAllocationName] = useState("");  //貯金名を入力するテキストボックスの値を管理
  const [allocationAmount,setAllocationAmount] = useState("");  //貯金金額を入力するテキストボックスの値を管理
  const [isPrivate,setIsPrivate] = useState("");  //公開か非公開の値を管理
  const [balance,setBalance] = useState(0);
  const [editingDrafts,setEditingDrafts] = useState<Record<string,string>>({});
  //全角入力を半角に変換し、数字以外の文字を除去する関数
  const normalizeAmount = (input: string) => {
    const half = input.replace(/[０-９]/g, s => String.fromCharCode(s.charCodeAt(0) - 0xFEE0)).replace(/ /g, '');
    const numeric = half.replace(/[^0-9]/g, '');
    return Number(numeric || 0);
  };
  //Firebaseに保存されている貯金名を取得
  useEffect(()=>{
    const userId = localStorage.getItem('userId');
    if(!userId) return;
    const year = selectedDate.getFullYear();
    const month = selectedDate.getMonth()+1;
    const savingUrl = `http://localhost:8080/api/savings/${userId}/${year}/${month}/summary`;
    const incomeUrl = `http://localhost:8080/api/incomes/${userId}/${year}/${month}/sum`;
    const paymentUrl = `http://localhost:8080/api/payments/${userId}/${year}/${month}/sum`;
    const ac = new AbortController();

    (async ()=>{
      
      try{
        const [resS,resI,resP] = await Promise.all([
          fetch(savingUrl,{ signal:ac.signal}),
          fetch(incomeUrl,{ signal:ac.signal}),
          fetch(paymentUrl,{ signal:ac.signal}),
        ]);
        const savingList:Array<{ name:string;amount?:string}>=
        await resS.json();
        setAllocations(
          (savingList || []).map(x =>({
            name:x.name,
            amount:String(x.amount ?? 0)
          }))
        );
        const [txtI,txtP] = await Promise.all([resI.text(),resP.text()]);
        if(!resI.ok) throw new Error(txtI || resI.statusText);
        if(!resP.ok) throw new Error(txtP || resP.statusText);

        const income = txtI ? JSON.parse(txtI):0;
        const payment = txtP ? JSON.parse(txtP):0;
        setBalance(Number(income ?? 0) - Number(payment ?? 0));
      }catch(e:any){
        if(e?.name !== 'AbortError'){
          console.error('取得失敗',e);
        }
      }
    })();
    return () => ac.abort();
  },[])
  const totalAllocated = useMemo(()=>{
    return allocations.reduce((sum,item) => {
      const raw = editingDrafts[item.name] ?? item.amount; 
    return  sum + normalizeAmount(raw)
    },0);
  
    },[allocations,editingDrafts]);

  const remaining = balance - totalAllocated;
  //データを保存する処理
  const handleSave = async() =>{
    if(allocations.length === 0 ){
      alert('保存する貯金名を追加してください');
      return;
    }
    
    try{
      const userId = localStorage.getItem("userId");
      const response = await fetch("http://localhost:8080/api/savings/save",{
        method:'POST',
        headers:{
          'Content-Type':'application/json'
        },
        body:JSON.stringify({
          userId,
          isPrivate:isPrivate === '非公開',
          savings:allocations.filter(a => a.name.trim() !== '')
            .map(a => ({
              name:a.name.trim(),
              allocations:[
                {
                  allocationAmount:normalizeAmount(a.amount),
                  savingDate:selectedDate.toISOString().split('T')[0]
                }
              ]
            }))   
        })
      });
      if(!response.ok){
        throw new Error('Failed to save saving')
      }
        onClose();
        onSaveSuccess();
    }catch(err){
      console.error('通信に失敗しました',err)
    }
  }
  const startEdit= (name:string,currentAmount:string) =>{
  setEditingDrafts(prev => ({...prev,[name]:currentAmount}));

  }
  const endEdit = (name:string) => {
    setEditingDrafts(prev => {
      const {[name]:_,...rest } = prev;
      return rest;
    })
  }

  //入力した貯金名を追加する処理
  const handleClick = () => {
    if(!allocationName.trim()) return;

    setAllocations(prev => [...prev,{name:allocationName.trim(),amount:allocationAmount || '0'}])
    setAllocationName('');
  }
  const saveEdit = (name:string)  =>{
    setAllocations(prev => 
      prev.map(a => a.name === name ? {...a,amount:String(normalizeAmount(editingDrafts[name]))}
      :a)
    );
    setEditingDrafts(prev => {
      const { [name]:_,...rest} = prev;
      return rest;
    })
  }
  //追加された貯金名を削除する処理
  const handleDelete = async(name:string) =>{
    
  }
  return (
    <div className="saving-container">
        <h1>振り分け可能金額</h1>
        <h1 style={{color:remaining > 0 ? 'white':'red'}}><AnimateNumber value={remaining}/></h1>
        <h2>振り分け先を選ぶ</h2>
        <div className='amount-form'>
      <ul className="allocation-list">
  {allocations.map((item) => {
    const isEditing = editingDrafts[item.name] != undefined;
    const draft = editingDrafts[item.name] ?? '';
    return (
      <li key={item.name} className="allocation-item">
        <span className="allocation-name">{item.name}・・・</span>

        {isEditing ? (
          <>
            <input
              type="text"
              className="allocation-input"
              value={draft}
              onChange={(e) => setEditingDrafts(prev =>({...prev,[item.name]:e.target.value}))}
              onKeyDown={(e) => {
                if(e.key === 'Enter') saveEdit(item.name);
                if (e.key === 'Escape') endEdit(item.name);
              }}
              autoFocus
            />
            <button className="icon" style={{marginRight:'5px'}} onClick={() => saveEdit(item.name)}> 
              保存
            </button>
          </>
        ) : (
          <>
            <span className='amount-contents'>{item.amount}</span>
            <button
              className="icon" style={{marginRight:'5px'}}
              onClick={() => startEdit(item.name, item.amount)}
            >
              <FaEdit />
            </button>
          </>
        )}

        <button
          onClick={() => handleDelete(item.name)}
          className="icon"
        >
          <FaTrash />
        </button>
      </li>
    );
  })}
</ul>
        </div>
        <div className='category-type'>
        <input type="text" placeholder="新しい貯金を入力..." value={allocationName} onChange={(e) => setAllocationName(e.target.value)} />
        <select value={isPrivate} onChange={(e)=>setIsPrivate(e.target.value)}>
            <option value=""></option>
            <option value="公開">公開</option>
            <option value="非公開">非公開</option>
        </select>
        <button onClick={handleClick}>追加</button>
        </div>
        <div className='saving-button'>
        <button onClick={handleSave}>保存</button>
        </div>
    </div>
  )
}

export default SavingAllocationModal