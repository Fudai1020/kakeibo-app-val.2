import { Link, useNavigate } from 'react-router-dom'
import logo from '../assets/MY HOUSEHOLD.png'
import '../styles/App.css'
import '../styles/register.css'
import { useState } from 'react'
import { AiOutlineEye, AiOutlineEyeInvisible, } from 'react-icons/ai'
const Register = () => {
    const [email,setEmail] = useState('');
    const [password,setPassword] = useState('');
    const [confirmPassword,setConfirmPassword] = useState('');
    const navigate = useNavigate();
    const [showPassword,setShowPassword] = useState(false);
    const [error,setError] = useState('');

    //メールアドレス・パスワード登録処理
    const handleRegister = async(e:React.FormEvent<HTMLFormElement>) => {
      e.preventDefault();
      if(password !== confirmPassword){
        setError('パスワードが一致しません');
        return;
      }
        try{
          const response = await fetch(`${import.meta.env.VITE_API_URL}/api/users/register`,{
            method:'POST',
            headers:{
              'Content-type':'application/json',
            },
            body:JSON.stringify({
              email,
              password,
              confirmPassword,
            }),
          });
          if(response.ok){
            alert('登録完了！');
            setError('');
            navigate('/');
          }else{
            const data = await response.json();
            setError(data.message || '登録に失敗しました');
          }
        }catch(error:any){
          setError('サーバーに接続できません'); 
        }
    }


  return (
    <div className='login-page'>
      <div className='login-box'>
      
      <img src={logo} alt="logo" className='login-logo'/>
      <form onSubmit={handleRegister}>
      <div className='gap-group'>
      <input type="email"  className='login-input' placeholder='メールアドレス' value={email} 
      onChange={(e) => setEmail(e.target.value)} required/>
      <div style={{position:'relative'}}>
      <input type={showPassword ? 'text':'password'}  
            className='login-input' 
            placeholder='パスワード' 
            value={password} 
      onChange={(e) => setPassword(e.target.value)} required/>
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
      onChange={(e) => setConfirmPassword(e.target.value)} required/>
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
      <button className='login-button' type='submit'>登録</button>
      </form>
      {error && <p style={{color:'red'}}>{error}</p>}
      </div>
    </div>
  )
}

export default Register