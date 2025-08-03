import { useEffect, useState } from "react";
import Header from "../components/Header";
import MonthNavigate from "../components/MonthNavigate";
import CategoryList from "../components/CategoryList";

import '../styles/category.css'
//新しくtransaction型というオブジェクトの型を定義
type Transaction = {
  id: string;
  amount: number;
  mainCategory: string;
  subCategory: string;
  memo: string;
  createdAt: Date;
};
const Category = () => {
  const [date, setDate] = useState(new Date()); //日付を管理
  const [transactions, setTransactions] = useState<Transaction[]>([]);  //取得するデータの管理
  const [type, setType] = useState<"income" | "payment">("payment"); // 収入 or 支出の選択の管理
  //dateかtypeのマウント時、ユーザのカテゴリデータを取得
  

  return (
    <div>
      <Header />
      <div className="category-month">
      <MonthNavigate date={date} setDate={setDate}/>
      </div>
      <div className="type-switch">
        <label className="radio-wrapper">
        <input type="radio" name="type" value="income" checked={type==='income'} onChange={()=>setType('income')}/>
        <span className="custom-radio"></span>
          収入
        </label>

        <label className="radio-wrapper">
          <input type="radio" name="type" value="payment" checked={type==='payment'} onChange={()=> setType('payment')} />
          <span className="custom-radio"></span>
          支出
        </label>
      </div>

      <div className="category-list">
      <CategoryList transactions={transactions} />
      </div>
    </div>
  );
};

export default Category;
