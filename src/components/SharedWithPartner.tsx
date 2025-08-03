import icon from '../assets/default.png'
import { useEffect, useState } from "react"
import {  useNavigate } from 'react-router-dom';

type Props = {
  partnerUid: string;
};

const PartnerProfile = ({ partnerUid }: Props) => {
  // 共有相手のプロフィール情報を管理する。各プロパティは存在しない可能性があるためoptional(?:)にしている。
  const [partnerData, setPartnerData] = useState<{
    name?: string;
    email?: string;
    photoURL?: string;
    memo?:string;
    sharedAt?:Date;
  } | null>(null);
  const navigate = useNavigate();
  //共有を停止する処理
  const stopShare = async()=>{
    

      navigate('/shared')
  }
//共有相手のUidマウント時、共有相手の情報を取得する


  return (
    <div className="main-container">
      <h1 style={{ marginTop: '30px', marginBottom: '10px' }}>共有中</h1>
      <div className="profile-icon">
        <img src={partnerData?.photoURL || icon} alt="" className="profile-img" />
      </div>
      <h2>{partnerData?.name || 'NoName'}</h2>
      <h2>{partnerData?.email || 'メール未設定'}</h2>
      <h2>共有開始日:{partnerData?.sharedAt?.toLocaleDateString() }</h2>
      <h2>{partnerData?.memo||'メモなし'}</h2>
      <button onClick={stopShare}>共有をやめる</button>
    </div>
  );
};

export default PartnerProfile;
