import { FaEdit, FaTrash } from "react-icons/fa";
import AnimateNumber from "./AnimateNumber";
import SubCategories from "./SubCategories";
import { useState } from "react";
//新しくtransaction型としてオブジェクトの型を定義
type Transaction = {
  id: string;
  amount: number;
  mainCategory: string;
  subCategory: string;
  memo: string;
  createdAt: Date;
};
//プロップスで受け取る値に方を当てはめる
type Props = {
  transactions: Transaction[];
};

const CategoryList = ({ transactions }: Props) => {
  //現在編集中のカテゴリ名を管理
  const [editCategory,setEditCategory] = useState<string | null>(null);
    //編集した内容の値を管理
  const [newCategory,setNewCategory] = useState('');
  //ユーザ認証

  //配列を一つのオブジェクトに変換する処理
  const grouped = transactions.reduce((acc, item) => {
    //指定された値が存在していなければ新しく配列を用意
    if (!acc[item.mainCategory]) acc[item.mainCategory] = [];
    acc[item.mainCategory].push(item);  //データを配列に追加
    return acc; //完成したオブジェクトを返す
  }, {} as Record<string, Transaction[]>); //初期値として空のオブジェクトを作成し、型を指定

  //指定された mainCategory のトランザクション全部を削除
  const handleDeleteCategory = async () => {
   
  };
  //編集モードの開始
  const startEdit = (category:string) =>{
    setEditCategory(category);
    setNewCategory(category);
  }
  //編集した内容を保存
  const handleSave = async()=>{
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
      {Object.entries(grouped).map(([mainCategory, subTransactions]) => {
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
                        if(e.key === "Enter") handleSave()
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
                    <button className='edit-button' onClick={handleSave}>保存</button>
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
                  onClick={() => handleDeleteCategory()}
                >
                  <FaTrash />
                </button>
                </div>
                )}
              </div>
            </div>
            <SubCategories subTransactions={subTransactions} />
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
