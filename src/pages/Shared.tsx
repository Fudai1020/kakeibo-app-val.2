import {  useState } from "react"
import Header from "../components/Header"
import SharedWithPartner from "../components/SharedWithPartner";
import NoPartnerView from "../components/NoPartnerView";
import useShareSubscribe from "../hooks/useShareSubscribe";

const Shared = () => {
  //共有相手のUidを管理
  const [sharedWith, setSharedWith] = useState<string | null>(null);
  const [loading, setLoading] = useState(false); //ローディング状態の管理
  const [partnerLeft, setPartnerLeft] = useState(false);  //共有相手の解除状態の管理
    const [partnerData, setPartnerData] = useState<{
    name?: string;
    email?: string;
    memo?:string;
    sharedAt?:string;
  } | null>(null);
  //ユーザのUidを保存
  const userId = localStorage.getItem("userId");

  useShareSubscribe(userId!,(msg)=>{
    console.log("WebSocketから届いた：",msg);

    if(msg === "partnerJoined"){
      fetch(`http://localhost:8080/api/shared/profile/${userId}`)
      .then((res) => res.json())
      .then((profile)=>{
        setSharedWith(userId!)
        setPartnerData(profile)
      });
    }else{
      setPartnerData(msg);
    }
  })

  if (loading) return <p style={{color:"white",textAlign:'center',marginTop:50}}>読み込み中...</p>;

  return (
    <div>
      <Header />
      <div className="shared-page">
        {/*partnerLeftがtrueの場合メッセージを表示*/}
        {partnerLeft ? (
          <div>
            <p>パートナーが共有を終了しました。</p>
            <button onClick={() => {setSharedWith(null); setPartnerLeft(false);}}>再読み込み</button>
          </div>
        ) : sharedWith ? (  //共有相手のUidが取得できていたら共有相手のプロフィールを表示
          <SharedWithPartner partnerData={partnerData}/>
        ) : (//共有していない場合NoPartnerViewコンポーネントを表示
          <NoPartnerView onShareSuccess={(uid: string,profile) => {setSharedWith(uid); setPartnerData(profile)}} />
        )}
      </div>
    </div>
  );
};


export default Shared;
