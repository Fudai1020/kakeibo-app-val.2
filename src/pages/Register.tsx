import { Link, useNavigate } from 'react-router-dom'
import logo from '../assets/MY HOUSEHOLD.png'
import '../styles/App.css'
import '../styles/register.css'
import { useState } from 'react'
import { AiOutlineEye, AiOutlineEyeInvisible, } from 'react-icons/ai'
const register = () => {
    const [email,setEmail] = useState('');
    const [password,setPassword] = useState('');
    const [confirmPassword,setConfirmPassword] = useState('');
    const navigate = useNavigate();
    const [showPassword,setShowPassword] = useState(false);

    //メールアドレス・パスワード登録処理
    const handleRegister = async() => {
        //登録処理
        try{
          //Firebaseの認証機能を使用してログイン、メールアドレスを登録
            
        alert('登録完了！')
        //ホーム画面に遷移
        navigate('/');
        }catch(error:any){
        alert('登録エラー') 
        }
    }


  return (
    <div className='login-page'>
      <div className='login-box'>
      
      <img src={logo} alt="logo" className='login-logo'/>
      <div className='gap-group'>
      <input type="email"  className='login-input' placeholder='メールアドレス' value={email} 
      onChange={(e) => setEmail(e.target.value)}/>
      <div style={{position:'relative'}}>
      <input type={showPassword ? 'text':'password'}  
            className='login-input' 
            placeholder='パスワード' 
            value={password} 
      onChange={(e) => setPassword(e.target.value)}/>
      <div 
      onClick={() => setShowPassword(!showPassword)}
      style={{position: 'absolute',
      top: '65%',
      right: '10px',
      transform: 'translateY(-50%)',
      cursor: 'pointer',}}
      >
        {showPassword ? <AiOutlineEyeInvisible className='eye-icon' style={{fontSize:'24px'}}/> : <AiOutlineEye className='eye-icon' style={{fontSize:'24px'}}/>}
      </div>
      </div>
      <div style={{position:'relative'}}>
        {/*showPasswordがtrueの場合属性をtext,falseの場合passwordに切り替え*/}
      <input type={showPassword ? 'text':'password'}  
            className='login-input' 
            placeholder='パスワードの確認' 
            value={confirmPassword} 
      onChange={(e) => setConfirmPassword(e.target.value)}/>
        <div 
            onClick={() => setShowPassword(!showPassword)}
            style={{position: 'absolute',
            top: '65%',
            right: '10px',
            transform: 'translateY(-50%)',
            cursor: 'pointer',}}
        >
        {showPassword ? <AiOutlineEyeInvisible className='eye-icon' style={{fontSize:'24px'}}/> : <AiOutlineEye className='eye-icon' style={{fontSize:'24px'}}/>}

      </div>
      </div>
      </div>
        <p className='login-text'>
           {/*ログイン画面に遷移*/}
        <Link to={'/'}>ログイン</Link>
        </p>
      <button className='login-button' onClick={handleRegister}>登録</button>

      </div>
    </div>
  )
}

export default register