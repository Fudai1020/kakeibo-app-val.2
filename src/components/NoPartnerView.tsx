import { useState } from "react";
import '../styles/noPartnerView.css'
import { apiFetch } from "../utils/api";
//親から渡される値の型を定義
type profileDto={
  id:number;
  name?:string;
  email?:string;
  memo?:string;
  sharedAt?:string;
}
type props = {
    onShareSuccess:(partnerUid:string,profile:profileDto)=>void;
}

const NoPartnerView = ({onShareSuccess}:props) => {
  //共有の始め方の管理
  const [mode, setMode] = useState<"start" | "join" | null>(null);
  //キーワードの管理
  const [keyword, setKeyword] = useState("");
  const [error, setError] = useState("");
  //ユーザ認証を行う
  //招待されたユーザが共有に参加する処理
  const handleJoinSharing = async () => {
    try{
      const partnerId = localStorage.getItem("userId");
      if(!partnerId){
        setError('ユーザIDが見つかりません');
          return;
      }
      const res = await apiFetch("http://localhost:8080/api/shared/join",{
        method:"POST",
        headers:{
          "Content-Type":"application/json",
        },
        body:JSON.stringify({
          partnerId:Number(partnerId),
          code:keyword,
        }),
      });
      if(!res.ok){
        throw new Error("参加できませんでした");
      }
      const profile = await res.json();
      localStorage.setItem("sharedWith",profile.id.toString());
      localStorage.setItem("partnerData",JSON.stringify(profile))
      onShareSuccess(profile.id.toString(),profile);
      setMode(null);
    }catch(err){
      console.error(err);
      setError("合言葉が違います");
    }
  };
  //共有を開始する処理
  const handleStartSharing = async () => {
    try{
      const ownerId = localStorage.getItem('userId');
      const res = await apiFetch(`http://localhost:8080/api/shared/generate/${ownerId}`,{
        method:"POST",
        headers:{"Content-Type":"application/json"}
      });
      if(!res.ok) throw new Error("合言葉生成に失敗しました");
      const generate = await res.text();
      setKeyword(generate);
      setMode("start");
    }catch(err){
      console.error(err);
      setError("エラーが発生しました");
    }
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
