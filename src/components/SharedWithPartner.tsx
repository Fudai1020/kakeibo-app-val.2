import icon from '../assets/default.png'
import {  useNavigate } from 'react-router-dom';

type profile={
  name?:string;
  email?:string;
  memo?:string;
  sharedAt?:string;
}
type  Props ={
  partnerData:profile|null;
  onStopShare:()=>void;
}

const PartnerProfile = ({ partnerData,onStopShare }: Props) => {
  // 共有相手のプロフィール情報を管理する。各プロパティは存在しない可能性があるためoptional(?:)にしている。

  const navigate = useNavigate();
  //共有を停止する処理
  const stopShare = async()=>{
    try{
      const userId = localStorage.getItem("userId");
      if(!userId) return;
      await fetch(`http://localhost:8080/api/shared/leave/${userId}`,{
        method:"DELETE",
      });
      localStorage.removeItem("sharedWith");
      localStorage.removeItem("partnerData");
      onStopShare();
      navigate("/shared");
    }catch(err){
      console.error(err)
    }
  }
//共有相手のUidマウント時、共有相手の情報を取得する


  return (
    <div className="main-container">
      <h1 style={{ marginTop: '30px', marginBottom: '10px' }}>共有中</h1>
      <div className="profile-icon">
        <img src={icon} alt="" className="profile-img" />
      </div>
      <h2>{partnerData?.name || 'NoName'}</h2>
      <h2>{partnerData?.email || 'メール未設定'}</h2>
      <h2>共有開始日:{partnerData?.sharedAt}</h2>
      <h2>{partnerData?.memo||'メモなし'}</h2>
      <button onClick={stopShare}>共有をやめる</button>
    </div>
  );
};

export default PartnerProfile;
