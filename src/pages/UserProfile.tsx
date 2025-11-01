import Header from "../components/Header"
import icon from '../assets/default.png'
import '../styles/userProfile.css'
import { useEffect, useState } from "react"
import { apiFetch } from "../utils/api"


const UserProfile = () => {
    //ユーザ情報を管理する各プロパティは存在しない可能性があるのでoptional(?:)にしている
    const [userData,setUserData] = useState<{name?:string,email?:string,memo?:string} | null>(null);
    //コンポーネントの初回マウント時、ログイン中のユーザ情報を取得してstateにセット
    useEffect(()=>{
      const id = localStorage.getItem('userId');
      if(!id) return;
      apiFetch(`http://localhost:8080/api/users/${id}`)
          .then(res => res.json())
          .then(data =>{
            setUserData(data);
          })
          .catch(err => console.error('Error',err));
    },[])

  return (
    <div className="main-container">
        <Header />
        <h1 style={{marginTop:'50px',marginBottom:'10px'}}>Account</h1>
        <div className="profile-icon">
        <img src={icon} alt="" className="profile-img"/>
        </div>
        {/*ユーザ名、メールアドレス、メモを表示（データがない場合は初期メッセージ）*/}
        <h2>{userData?.name || 'NoName'}</h2>
        <h2>{userData?.email || 'メール未設定'}</h2>
        <h2>一言メモ</h2>
        <h2 style={{marginTop:'10px'}}>{userData?.memo || 'メモはありません'}</h2>
    </div>
  )
}

export default UserProfile