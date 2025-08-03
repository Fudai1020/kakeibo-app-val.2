import { Link, useNavigate } from 'react-router-dom';
import logo from '../assets/MY HOUSEHOLD.png'
import '../styles/App.css'
import '../styles/login.css'
import { useState } from 'react';
import { AiOutlineEye, AiOutlineEyeInvisible } from 'react-icons/ai';
const Login = () => {
  const [email,setEmail] = useState('');
  const [password,setPassword] = useState('');
  const [showPassword,setShowPassword] = useState(false);
  const navigate = useNavigate();

  //ログイン処理  
  const handleLogin =async () => {

    //ログイン処理、エラーの場合アラートで返す
   
      navigate('/Home')
   
  }
  return (
    <div className='login-page'>
      <div className='login-box'>
      
      <img src={logo} alt="logo" className='login-logo'/>
      <div className='gap-group'>
      <input type='email'  className='login-input' placeholder='メールアドレス' value={email}
      onChange={(e) => setEmail(e.target.value)}/>
      <div style={{position:'relative'}}>
      <input type={showPassword ? 'text':'password'}  
        className='login-input' placeholder='パスワード' value={password}
        onChange={(e) => setPassword(e.target.value)}/>
         <div 
            onClick={() => setShowPassword(!showPassword)}
            style={{position: 'absolute',
            top: '65%',
            right: '10px',
            transform: 'translateY(-50%)',
            cursor: 'pointer',}}
          >
          {/*パスワード表示・非表示の切り替えアイコン */}
        {showPassword ? <AiOutlineEyeInvisible className='eye-icon' style={{fontSize:'24px'}}/> : <AiOutlineEye className='eye-icon' style={{fontSize:'24px'}}/>}
        </div>
      </div>
      <p className="register-text">
          アカウントが未登録ですか？ <Link to="/Register">アカウントの作成</Link>
      </p>
      <button onClick={handleLogin} className='login-button'>ログイン</button>

      </div>
    </div>
    </div>
  )
};

export default Login;