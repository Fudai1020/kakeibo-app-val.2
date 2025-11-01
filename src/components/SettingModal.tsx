import { Link, useNavigate } from 'react-router-dom'
import '../styles/settingmodal.css'


const SettingModal = () => {
  const navigate = useNavigate(); //フックスを定義する
  //ログアウト処理
  const logout = async() =>{
    localStorage.removeItem("userId");
    localStorage.removeItem("token");
    navigate('/'); //ログイン画面遷移
    
  }
  return (
    <div className="side-contain">
        <div className="settings">
            <h1>設定</h1>
            {/*リンクを押下時、編集画面、パスワード変更画面にそれぞれ遷移*/}
            <Link to={('/editProfile')}><h2>プロフィールを編集する</h2></Link>
            <Link to={('/ChangePassword')}><h2>パスワードを変更する</h2></Link>
        </div>
        <div className="notification">
        <h1>通知設定</h1>
        <label>
            <input type="checkbox" />
            支出上限通知
        </label>
                <label>
        <input type="checkbox" />
            月末リマインド
        </label>
        </div>
        <button className='logout-button' onClick={logout}>ログアウト</button>
    </div>
  )
}

export default SettingModal