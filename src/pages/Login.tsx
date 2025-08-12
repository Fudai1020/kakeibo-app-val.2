import { Link, useNavigate } from 'react-router-dom';
import logo from '../assets/MY HOUSEHOLD.png'
import '../styles/App.css'
import '../styles/login.css'
import { useState, type FormEvent } from 'react';
import { AiOutlineEye, AiOutlineEyeInvisible } from 'react-icons/ai';
const Login = () => {
  const [email,setEmail] = useState('');
  const [password,setPassword] = useState('');
  const [showPassword,setShowPassword] = useState(false);
  const [error,setError] = useState('');
  const navigate = useNavigate();

  //ログイン処理  
  const handleLogin = async (e:FormEvent) => {
    e.preventDefault();
    if(!email.includes('@')){
      setError('有効なメールアドレスを入力してください');
    }
    try{
      const response = await fetch('http://localhost:8080/api/users/login',{
        method:'POST',
        headers:{
          'Content-Type':'application/json',
        },
        body:JSON.stringify({
          email,
          password,
        }),
      });
      if(response.ok){
        const data = await response.json();
        localStorage.setItem('userId',data.id);
        alert('ログイン成功！');
        setError('');
        navigate('/Home')
      }else{
        const data = await response.json();
        setError(data.message || 'ログインに失敗しました')
      }
    }catch(error){
      setError('通信エラーが発生しました');
    }
      
   
  }
  return (
    <div className='login-page'>
      <div className='login-box'>
      
      <img src={logo} alt="logo" className='login-logo'/>
      <form onSubmit={handleLogin}>
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
      <button type="submit" className='login-button'>ログイン</button>
      </div>
      </form>
      {error && <p style={{color:'red'}}>{error}</p>}
    </div>
  </div>
  )
};

export default Login;