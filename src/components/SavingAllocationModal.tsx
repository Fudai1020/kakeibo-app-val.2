import { useEffect, useState } from 'react';
import '../styles/savingAllocationModal.css'
import { FaTrash } from 'react-icons/fa';
import AnimateNumber from './AnimateNumber';
//プロップスの方を指定する
type props = {
    onClose:() => void;
    balance:number;
  selectedDate:Date;
}
const SavingAllocationModal = ({onClose,balance,selectedDate}:props) => {
  const [allocations,setAllocations] = useState<{name:string;amount:string}[]>([]); //データの値をオブジェクトとして管理
  const [allocationName,setAllocationName] = useState("");  //貯金名を入力するテキストボックスの値を管理
  const [allocationAmount,setAllocationAmount] = useState("");  //貯金金額を入力するテキストボックスの値を管理
  const [isPrivate,setIsPrivate] = useState("");  //公開か非公開の値を管理
  const [totalAllcation,setTotalAllcation] = useState(balance);
  //全角入力を半角に変換し、数字以外の文字を除去する関数
  const normalizeAmount = (input: string) => {
    const half = input.replace(/[０-９]/g, s => String.fromCharCode(s.charCodeAt(0) - 0xFEE0)).replace(/ /g, '');
    const numeric = half.replace(/[^0-9]/g, '');
    return Number(numeric);
  };
  //Firebaseに保存されている貯金名を取得
  
  //データを保存する処理
  const handleSave = async() =>{
    
  }

  //入力した貯金名を追加する処理
  const handleClick = () => {
    
  }
  //追加された貯金名を削除する処理
  const handleDelete = async(name:string) =>{
    
  }
  useEffect(()=>{
    const totalAllocated = allocations.reduce((sum,item)=>{
      return sum + normalizeAmount(item.amount);
    },0);
    setTotalAllcation(balance - totalAllocated);

  },[balance,allocations])
  return (
    <div className="saving-container">
        <h1>振り分け可能金額</h1>
        <h1 style={{color:totalAllcation > 0 ? 'white':'red'}}><AnimateNumber value={totalAllcation}/></h1>
        <h2>振り分け先を選ぶ</h2>
        <div className='amount-form'>
        <ul className='allocation-list'>
          {/*取得したデータをリスト表示*/}
          {allocations.map((item,index)=>(
        <li key={index} className="allocation-item">
        <span className="allocation-name">{item.name}・・・</span>
        <input type="text" className="allocation-input" value={item.amount} 
          onChange={(e) => {
          const newAllocations = [...allocations]; //取得したデータの配列を新しくコピー
          newAllocations[index].amount = e.target.value; //入力された値を保存
          setAllocations(newAllocations); //更新した配列をstateで更新
        }}
        />
      <button onClick={() => handleDelete(item.name)} className="saving-delete">
        <FaTrash />
      </button>
      </li>

          ))}
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