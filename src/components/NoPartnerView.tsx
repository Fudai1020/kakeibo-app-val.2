import { useState } from "react";
import '../styles/noPartnerView.css'
//親から渡される値の型を定義
type props = {
    onShareSuccess:(partnerUid:string)=>void;
}

const NoPartnerView = ({onShareSuccess}:props) => {
  //共有の始め方の管理
  const [mode, setMode] = useState<"start" | "join" | null>(null);
  //キーワードの管理
  const [keyword, setKeyword] = useState("");
  const [error, setError] = useState("");
  //ユーザ認証を行う
  //合言葉を作成する処理
  const generateKeyword = () => {
    const chars = "abcdefghijklmnopqrstuvwxyz0123456789"; //合言葉で使用する文字列
    return Array.from({ length: 6 }, //要素数が６つの配列を作成
      () => chars[Math.floor(Math.random() * chars.length)]).join("");//インデックスの範囲内の数で整数に変換してランダムに配列に格納して結合
  };
  //共有を開始する処理
  const handleStartSharing = async () => {

  };
  //招待されたユーザが共有に参加する処理
  const handleJoinSharing = async () => {
    
  };
  //共有モードの終了
  const handleBack = () =>{
    setMode(null);
  }

  return (
    <div className="no-partner-view">
      {/*共有モードの状態管理*/}
      {mode === null && (
        <div className="share-options">
          <h2>共有を開始しますか？</h2>
          <button onClick={handleStartSharing}>合言葉を作成する</button>
          <button onClick={() => setMode("join")}>合言葉を入力する</button>
        </div>
      )}

      {mode === "start" && (
        <div className="share-start">
          <p style={{fontWeight:'bold',fontSize:20}}>この合言葉を相手に伝えてください：</p>
          <h3 className="keyword">{keyword}</h3>
          <button onClick={handleBack}>戻る</button>
        </div>
      )}

      {mode === "join" && (
        <div className="share-join">
          <p style={{fontWeight:'bold',fontSize:20}}>相手から教えてもらった合言葉を入力してください</p>
          <input
            value={keyword}
            onChange={(e) => setKeyword(e.target.value)}
          />
          <div className='buttons'>
          <button onClick={handleJoinSharing}>共有に参加</button>
          <button onClick={handleBack}>戻る</button>
          </div>
          {error && <p className="error-text">{error}</p>}
        </div>
      )}
    </div>
  );
};

export default NoPartnerView;
