  import { useEffect, useState } from 'react';
  import '../styles/transactionFormModal.css';
import { apiFetch } from '../utils/api';

  //親から渡されたpropsの方を定義する
  type Props = {
    onClose: () => void;
    type: 'income' | 'payment';
    incomeCategories: string[];
    setIncomeCategories: React.Dispatch<React.SetStateAction<string[]>>;//親から渡されたset関数をstring型の配列で定義
    paymentCategories: string[];
    setPaymentCategories: React.Dispatch<React.SetStateAction<string[]>>;
    onSaveSuccess:()=>void;
  };

  const TransactionFormModal = ({
    onClose,
    type: initialType, //typeの名前を変えている中身はincomeかpayment
    incomeCategories,
    setIncomeCategories,
    paymentCategories,
    setPaymentCategories,
    onSaveSuccess
  }: Props) => {
    const [view, setView] = useState<'form' | 'category'>('form'); //モーダル内の画面の切り替えの管理
    const [slideDirection, setSlideDirection] = useState<'left' | 'right' | null>(null); //アニメーションの方向の管理
    const [animeClass, setAnimeClass] = useState(''); //アニメーションのクラスの管理
    const [mainCategoryInput, setMainCategoryInput] = useState(''); //メインカテゴリを入力するテキストボックス内の入力値を管理
    const [selectedMainCategory, setSelectedMainCategory] = useState(''); //セレクトボックス内の値を管理
    const [type, setType] = useState<'income' | 'payment'>(initialType); //モーダル内の支出、収入の切り替えを管理※初期値は親から渡されたpropsのtype
    const [amount, setAmount] = useState(''); //金額を入力するテキストボックスの入力値を管理
    const [subCategory, setSubCategory] = useState(''); //サブカテゴリを入力するテキストボックスの値を管理
    const [memo, setMemo] = useState(''); //メモを入力するテキストエリアの入力値を管理
    const [date, setDate] = useState(''); //登録日の値を管理
    const [isPrivate, setIsPrivate] = useState(false); //表示、非表示の選択を管理

    //テキストボックスに入力される値が全角入力の時、半角の数値に嫌韓する処理
    const normalizeAmount = (input: string) => {
      //テキストボックス内の文字を受け取りコードユニットに変換
      const half = input.replace(/[０-９]/g, s => String.fromCharCode(s.charCodeAt(0) - 0xFEE0)).replace(/ /g, '');
      const numeric = half.replace(/[^0-9]/g, ''); //数字以外の除去
      return Number(numeric); //数値型で返す
    };

    useEffect(()=>{
      const userId = localStorage.getItem('userId');
      const settype = type == 'income' ? 'incomes':'payments';

      apiFetch(`http://localhost:8080/api/${settype}/user/${userId}`)
        .then(res => res.json())
        .then((data:string[])=>{
          if(type === 'income'){
            setIncomeCategories(data);
          }else{
            setPaymentCategories(data);
          }
        })
        .catch(err => console.error('カテゴリ取得失敗',err));
    },[type])

    //フォームの入力値をデータベースに保存する処理
    const handleSubmit = async () => {

      try{
        const userId = localStorage.getItem('userId');
      const payload :any = {
        name:subCategory,
        amount:normalizeAmount(amount),
        memo:memo,
        isPrivate:isPrivate,
        userId:userId ? Number(userId) : null
      };
      if(type === 'income'){
        payload.incomeDate = date;
        payload.incomeCategoryName = selectedMainCategory;
      }else{
        payload.paymentDate = date;
        payload.paymentCategoryName = selectedMainCategory;
      }
      const url = type === 'income' ? 'http://localhost:8080/api/incomes/save' : 'http://localhost:8080/api/payments/save'
    
      const response = await apiFetch(url,{
        method:'POST',
        headers:{'Content-Type':'application/json'},
        body:JSON.stringify(payload),
      });
      if(response.ok){
        onSaveSuccess(); 
        onClose(); 
      }else{
        const errorMessage = await response.text();
        throw new Error(errorMessage || '保存に失敗しました。')
        
      }
    }catch(error){
      console.error(error);
      alert('保存に失敗しました。')
    }
      

    };
    //カテゴリモーダルを開く処理
    const openCategoryModal = () => {
      setSlideDirection('right');
      setAnimeClass('slide-in-right');
      setView('category');
    };
    //カテゴリモーダルを閉じる処理
    const closeCategoryModal = () => {
      setSlideDirection('left');
      setAnimeClass('slide-in-left');
      setView('form');
    };
    //カテゴリをデータベースに追加する処理
    const addCategory = async (name: string, type: 'income' | 'payment') => {
      if(!name) return;

      if(type === 'income') {
        setIncomeCategories(prev => [...prev,name]);
      } else{
        setPaymentCategories(prev => [...prev,name]);
      }
      setMainCategoryInput('');
      setSelectedMainCategory(name);
    };


    //セレクトボックスのオプションとして表示する値
    const getCurrentMainCategories = () => (type === 'income' ? incomeCategories : paymentCategories);

    return (
      <div className="modal-container">
        {/* フォーム画面 */}
        {view === 'form' && (
          <div className={`content ${animeClass}`}>
            <div className="radio-group">
              <label>
                <input type="radio" value="income" checked={type === 'income'} onChange={() => setType('income')} />
                収入
              </label>
              <label>
                <input type="radio" value="payment" checked={type === 'payment'} onChange={() => setType('payment')} />
                支出
              </label>
            </div>

            <div className="input-amount">
              <input type="text" value={amount} onChange={(e) => setAmount(e.target.value)} placeholder="金額" />
            </div>

            <div className="memo">
              <h2>メモ</h2>
              <textarea value={memo} onChange={(e) => setMemo(e.target.value)} />
            </div>

            <div className="date-lay">
              <h2>日付</h2>
              <input type="date" value={date} onChange={(e) => setDate(e.target.value)} />
            </div>

            <div className="radio-group">
              <h2>公開設定</h2>
              <label>
                <input type="radio" checked={!isPrivate} onChange={() => setIsPrivate(false)} />
                公開
              </label>
              <label>
                <input type="radio" checked={isPrivate} onChange={() => setIsPrivate(true)} />
                非公開
              </label>
            </div>

            <button className="index-button" onClick={openCategoryModal}>
              項目を追加
            </button>
          </div>
        )}

        {/* カテゴリ選択・追加画面 */}
        {view === 'category' && (
          <div className={`content ${animeClass}`}>
            <div className="category-container">
              <h1 style={{ marginBottom: -15 }}>項目</h1>

              <div className="input-category">
                <h2>カテゴリ名</h2>
                <input type="text" value={subCategory} onChange={(e) => setSubCategory(e.target.value)} />

                <h2 style={{ marginTop: -15 }}>大カテゴリの選択</h2>
                <select value={selectedMainCategory} onChange={(e) => setSelectedMainCategory(e.target.value)}>
                  <option value=""></option>
                  {getCurrentMainCategories().map((item, index) => (
                    <option key={index} value={item}>
                      {item}
                    </option>
                  ))}
                </select>
              </div>

              <div className="category-content">
                <h2>新しい大カテゴリを追加</h2>
                <div className="input-with-button">
                  <input
                    type="text"
                    value={mainCategoryInput}
                    onChange={(e) => setMainCategoryInput(e.target.value)}
                  />
                  <button onClick={() => addCategory(mainCategoryInput, type)}>追加</button>
                </div>
              </div>

              <button className="back-button" onClick={closeCategoryModal}>
                戻る
              </button>
              <button className="save-button" onClick={handleSubmit}>
                保存
              </button>
            </div>
          </div>
        )}
      </div>
    );
  };

  export default TransactionFormModal;
