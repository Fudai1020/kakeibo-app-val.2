import { useState } from "react"
import Header from "../components/Header"
import '../styles/changePassword.css'

import { AiOutlineEye, AiOutlineEyeInvisible } from "react-icons/ai";


const ChangePassword = () => {
    //パスワードの内容を管理する
    const [currentPassword,setCurrentPassword] = useState('');
    const [newPassword,setNewPassword] = useState('');
    const [confirmPassword,setConfirmPassword] = useState('');
    //エラー状態を管理
    const [error,setError] = useState('');
    //パスワード表示の状態を管理
    const [showCurrentPassword,setCurrentShowPassword] = useState(false);
    const [showNewPassword,setShowNewPassword] = useState(false);
    const [showConfirmPassword,setShowConfirmPassword] = useState(false);

    //パスワードの変更の処理
    const handlePasswordChange = async()=>{
        const id = localStorage.getItem('userId');
        if(newPassword != confirmPassword){
            setError('パスワードが一致していません');
            return;
        }
        try{
            const response = await fetch(`http://localhost:8080/api/users/${id}/changePassword`,{
                method:'PUT',
                headers:{
                    'Content-Type':'application/json',
                },
                body:JSON.stringify({
                    password:currentPassword,
                    newPassword:newPassword,
                }),
            });
            if(response.ok){
                alert('パスワードを変更しました');
                setError('');
                setCurrentPassword('');
                setNewPassword('');
                setConfirmPassword('');
            }else{
                const text = await response.text();
                console.log(text);
                try{
                const errorData = await response.json();
                console.log(errorData);
                setError(errorData.message);
                }catch(e){
                    setError('サーバーから不正なレスポンスが返されました')
                }
            }
        }catch(error){
            setError('通信に失敗しました。');
        }
    }
  return (
    <div>
        <Header />
        <div className="password-inputs">
            <h1>パスワードを変更する</h1>
            <div style={{position:'relative'}}>
            <label >現在のパスワード
            <input type={showCurrentPassword?'text':'password'} value={currentPassword} onChange={(e)=>setCurrentPassword(e.target.value)}/>
            <div onClick={()=>setCurrentShowPassword(!showCurrentPassword)}
                style={{position:"absolute",
                        top:'65%',
                        right:'10px',
                        transform:'translateY(-20%)',
                        cursor:'pointer'
                }}>
                {showCurrentPassword ? <AiOutlineEyeInvisible style={{fontSize:'24px'}}/>:<AiOutlineEye style={{fontSize:'24px'}}/>}
            </div>
            </label>
            </div>
            <div style={{position:'relative'}}>
            <label >新しいパスワード
            <input type={showNewPassword?'text':'password'} value={newPassword} onChange={(e)=>setNewPassword(e.target.value)}/>
            <div onClick={()=>setShowNewPassword(!showNewPassword)}
                style={{position:"absolute",
                        top:'65%',
                        right:'10px',
                        transform:'translateY(-20%)',
                        cursor:'pointer'
                }}>
                {showNewPassword ? <AiOutlineEyeInvisible style={{fontSize:'24px'}}/>:<AiOutlineEye style={{fontSize:'24px'}}/>}
            </div>
            </label>
            </div>
            <div style={{position:'relative'}}>
            <label >再入力
            <input type={showConfirmPassword?'text':'password'} value={confirmPassword} onChange={(e)=>setConfirmPassword(e.target.value)}/>
            <div onClick={()=>setShowConfirmPassword(!showConfirmPassword)}
                style={{position:"absolute",
                        top:'65%',
                        right:'10px',
                        transform:'translateY(-20%)',
                        cursor:'pointer'
                }}>
                {showConfirmPassword ? <AiOutlineEyeInvisible style={{fontSize:'24px'}}/>:<AiOutlineEye style={{fontSize:'24px'}}/>}
            </div>
            </label>
            </div>
            {error && <p style={{color:'red'}}>{error}</p>}
            <button onClick={handlePasswordChange}>保存</button>
        </div>
    </div>
  )
}

export default ChangePassword