import { useEffect, useState } from "react"
import Header from "../components/Header"
import Income from "../components/Income"
import MonthNavigate from "../components/MonthNavigate"
import Payment from "../components/Payment"
import Saving from "../components/Saving"
import "../styles/home.css"
import TransactionFormModal from "../components/TransactionFormModal"
import { Modal } from "../components/Modal"
import SavingAllocationModal from "../components/SavingAllocationModal"


const Home = () => {
  const [modalType,setModalType] = useState<"transaction" | "saving" |null>(null); //モーダルのタイプを管理
  const [isopenModal,setIsopenModal] = useState(false); //モーダルの開閉管理
  const [savingBalance,setSavingBalance] = useState(0); //収入かた支出を引いた値を管理
  const [selectDate,setSelectDate] = useState(new Date());  //表示する月の管理
  const [transactionTyoe,setTransactionType] = useState<'income' | 'payment'>('income')  //支出と収入の種類管理
  const [incomeCategories, setIncomeCategories] = useState<string[]>([]); //収入のカテゴリを配列で管理
  const [paymentCategories, setPaymentCategories] = useState<string[]>([]); //支出のカテゴリを配列で管理
  const [sharedWith,setSharedWith] = useState<string|null>(null); //共有相手のuidを管理
  const [partnerName,setPartnerName] = useState<string|null>(null); //共有相手の名前を管理
  const [refreshTrigger,setRefreshTrigger] = useState(0);

  //コンポーネントの初回マウント時にユーザデータを取得

  //モーダルの開閉処理
  const openModal = () => setIsopenModal(true);
  const closeModal = () => setIsopenModal(false);
  const handleSaveSuccess = ()=>{
    setRefreshTrigger(prev => prev + 1);
  }

  return (
    <div>
    <Header />{/*ヘッダーコンポーネントを表示*/}
    {/*モーダル開閉管理*/}
    {isopenModal && ( /*isopenModalがtrueの場合modalコンポーネントを表示*/
      <Modal onClose={closeModal}>
        {/*modalTypeの値によって表示するコンポーネントを切り替え*/}
        {modalType === "transaction" &&(
        <TransactionFormModal 
        onClose={closeModal} type={transactionTyoe} incomeCategories={incomeCategories} 
        setIncomeCategories={setIncomeCategories}paymentCategories={paymentCategories}
        setPaymentCategories={setPaymentCategories} onSaveSuccess={handleSaveSuccess}/>
        )}
        {modalType === "saving" &&(
          <SavingAllocationModal onClose={closeModal} balance={savingBalance} selectedDate={selectDate}/>
        )}
      </Modal>
    )}
    <div className="home-layout">
      <div className="month-layout">
        {/*子コンポーネントに現在の日付と更新関数を渡す*/}
      <MonthNavigate date={selectDate} setDate={setSelectDate} />
      </div>
      <div className="main-content">
        <div className="left-section">
          <div className="income-layout">
            <Income onAddClick={()=>{setTransactionType('income');setModalType("transaction");openModal();}} 
            setModalType={setModalType} selectedDate={selectDate} sharedWith={sharedWith} refreshTrigger={refreshTrigger}/>
          </div>
          <div className="saving-layout">
          <Saving onAddClick={openModal} setModalType={setModalType} onBalanceChange={setSavingBalance} selectedDate={selectDate} sharedWith={sharedWith} />
          </div>
        </div>  
        <div className="payment-layout">
          <Payment onAddClick={()=>{setTransactionType('payment');setModalType("transaction");openModal();}} 
            setModalType={setModalType} selectedDate={selectDate} sharedWith={sharedWith} partnerName={partnerName} refreshTrigger={refreshTrigger}/>
        </div>
      </div>
      </div>
    </div>
  )
}

export default Home