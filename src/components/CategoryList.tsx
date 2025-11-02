import { FaEdit, FaTrash } from "react-icons/fa";
import AnimateNumber from "./AnimateNumber";
import SubCategories from "./SubCategories";
import { useState } from "react";
import { apiFetch } from "../utils/api";
//新しくtransaction型としてオブジェクトの型を定義
type Transaction = {
  id: string;
  amount: number;
  mainCategory: string;
  categoryId:string|null;
  subCategory: string;
  memo: string;
  createdAt: Date;
};
//プロップスで受け取る値に方を当てはめる
type Props = {
  transactions: Transaction[];
  onRefresh:()=>void;
  type:'income'|'payment';
};

const CategoryList = ({ transactions,onRefresh,type}: Props) => {
  //現在編集中のカテゴリ名を管理
  const [editCategory,setEditCategory] = useState<string | null>(null);
    //編集した内容の値を管理
  const [newCategory,setNewCategory] = useState('');
  //ユーザ認証

  //配列を一つのオブジェクトに変換する処理
 const grouped = transactions.reduce((acc, item) => {
  if (!acc[item.mainCategory]) {
    acc[item.mainCategory] = {
      id: item.categoryId,   // ← プロパティ名も統一
      transactions: []           // ← スペル修正
    };
  }
  acc[item.mainCategory].transactions.push(item); // ← 正しいpush先
  return acc;
}, {} as Record<string, { id: string | null; transactions: Transaction[] }>);
//初期値として空のオブジェクトを作成し、型を指定

  //指定された mainCategory のトランザクション全部を削除
  const handleDeleteCategory = async (categoryId:string | null) => {
   if(!categoryId) return;
   if(!window.confirm('削除しますか？')) return;
   try{
    const res = await apiFetch(`${import.meta.env.VITE_API_URL}/api/${type}s/deleteCategory/${categoryId}`,{
      method:'DELETE',
    });
    if(!res.ok) throw new Error('削除失敗');
    onRefresh(); 
   }catch(err){
    console.error(err);
   }

  };
  //編集モードの開始
  const startEdit = (category:string) =>{
    setEditCategory(category);
    setNewCategory(category);
  }
  //編集した内容を保存
  const handleSave = async(categoryId:string|null)=>{
    try{
      const res = await apiFetch(`${import.meta.env.VITE_API_URL}/api/${type}s/updateCategory/${categoryId}`,{
        method:'PUT',
        headers:{'Content-Type':'application/json'},
        body:JSON.stringify({
          name:newCategory
        })
      });
    if(!res.ok) throw new Error('Failed to update');
    onRefresh();
    setEditCategory(null);
    }catch(err){
      console.error(err);
    }
  };
  //編集を中断する処理
  const canselEdit = ()=>{
    setEditCategory(null);
    setNewCategory('');
  }
  //データの金額を合計
  const totalAmount = transactions.reduce((sum,item) => sum + item.amount,0);


  return (
    <>
    <div className="overflow-category">
      {/*オブジェクトを配列に変換し、グループの中でカテゴリ別の合計金額を計算*/}
      {Object.entries(grouped).map(([mainCategory, {id:categoryId,transactions:subTransactions}]) => {
        const total = subTransactions.reduce((sum, item) => sum + item.amount, 0);
        return (
          <div key={mainCategory} className="category-block">
            <div className="category-header">
              {/*編集状態の場合テキストボックスの表示*/}
                {editCategory === mainCategory ?(
                    <input type="text"
                    className="edit-category"
                    value={newCategory} 
                    onChange={(e)=>setNewCategory(e.target.value)}
                    onKeyDown={(e)=>{
                        if(e.key === "Enter") handleSave(categoryId)
                        if(e.key === 'escape') canselEdit()}
                    }
                    autoFocus
                    />
                    
                ):(
              <h3>
                <span className="category-title">
                  {mainCategory}・・・合計：<AnimateNumber value={total} />円
                </span>
              </h3>
                )}
              <div className="category-actions">
                {editCategory===mainCategory ? (
                    <div className="edit-buttons">
                    <button className='edit-button' onClick={()=>handleSave(categoryId)}>保存</button>
                    <button className="edit-button" onClick={canselEdit}>キャンセル </button>
                    </div>
                ):(
                <div className="icon-buttons">
                <button className="icon-button"
                onClick={()=>startEdit(mainCategory)}>
                  <FaEdit />
                </button>
                
                <button
                  className="icon-button"
                  onClick={() => handleDeleteCategory(categoryId)}
                >
                  <FaTrash />
                </button>
                </div>
                )}
              </div>
            </div>
            <SubCategories subTransactions={subTransactions} onRefresh={onRefresh} type={type}/>
          </div>
        );
      })}
    </div>
     <div className="total-amount">
      <h2>合計：<AnimateNumber value={totalAmount}/></h2>
      </div>
    </>
  );
};

export default CategoryList;
