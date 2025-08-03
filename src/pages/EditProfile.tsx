import Header from "../components/Header"
import { getAuth } from "firebase/auth";
import { doc, getDoc, updateDoc } from "firebase/firestore";
import { useEffect, useState } from "react";
import'../styles/editProfile.css';
import icon from '../assets/default.png'
import { useNavigate } from "react-router-dom";




const EditProfile = () => {
    //編集する値の入力値をそれぞれstateで管理
    const [newName,setNewName] = useState('');
    const [newMemo,setNewMemo] = useState('');
    //編集される前の情報を管理
    const [originalName,setOriginalName] = useState('');
    const [originalMemo,setOriginalMemo] = useState('');
    const navigate = useNavigate();
    //Firebaseからユーザ情報を初回マウント時取得
   
//編集したユーザ情報を保存
const handleEditSave = async() =>{

    try{
        //データが空白だったら元のデータを保存し、データがあれば新しいデータを保存する
       
        //完了アラートを出す
        alert('編集完了！');
        //テキストボックスを空欄にする
        setNewName('');
        setNewMemo('');
        //プロフィール画面に遷移
        navigate('/userProfile');
    }catch(error){
        console.error('更新に失敗しました',error);
    }
    }

  return (
    <div>
        <Header />
        <div className="main">
        <h1 style={{marginTop:'20px',marginBottom:'-10px'}}>Account</h1>
        <div className="profile-icon">
        <img src={icon} alt="" className="profile-img"/>
        </div>
        <label className="edit-name">名前
        <input type="text" placeholder={originalName} value={newName} onChange={(e)=>setNewName(e.target.value)}/>
        </label>
        <label className="edit-memo" > 一言メモ
        <textarea value={newMemo} placeholder={originalMemo} onChange={(e)=>setNewMemo(e.target.value)}></textarea>
        </label>
        <button onClick={handleEditSave}>変更</button>
        </div>
    </div>
  )
}

export default EditProfile