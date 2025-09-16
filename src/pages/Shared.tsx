import { useEffect, useState } from "react"
import Header from "../components/Header"
import SharedWithPartner from "../components/SharedWithPartner";
import NoPartnerView from "../components/NoPartnerView";

const Shared = () => {
  //共有相手のUidを管理
  const [sharedWith, setSharedWith] = useState<string | null>(null);
  const [loading, setLoading] = useState(false); //ローディング状態の管理
  const [partnerLeft, setPartnerLeft] = useState(false);  //共有相手の解除状態の管理
  //ユーザのUidを保存
  

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
        ) : loading ? (  //共有相手のUidが取得できていたら共有相手のプロフィールを表示
          <SharedWithPartner partnerUid={loading} />
        ) : (//共有していない場合NoPartnerViewコンポーネントを表示
          <NoPartnerView onShareSuccess={(uid: string) => setSharedWith(uid)} />
        )}
      </div>
    </div>
  );
};


export default Shared;
