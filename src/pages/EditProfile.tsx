import Header from "../components/Header"
import { useEffect, useState } from "react";
import'../styles/editProfile.css';
import icon from '../assets/default.png'
import {  useNavigate } from "react-router-dom";
import { apiFetch } from "../utils/api";




const EditProfile = () => {
    //編集する値の入力値をそれぞれstateで管理
    const [newName,setNewName] = useState('');
    const [newMemo,setNewMemo] = useState('');
    //編集される前の情報を管理
    const [originalName,setOriginalName] = useState('');
    const [originalMemo,setOriginalMemo] = useState('');
    const navigate = useNavigate();
    //Firebaseからユーザ情報を初回マウント時取得
   useEffect(()=>{
    const id = localStorage.getItem('userId');
    fetch(`http://localhost:8080/api/users/${id}`)
        .then(res => res.json())
        .then(data =>{
            setOriginalName(data.name);
            setOriginalMemo(data.memo);
        })
   },[])
//編集したユーザ情報を保存
const handleEditSave = async() =>{
    const id = localStorage.getItem('userId');

    try{
        const response = await apiFetch(`http://localhost:8080/api/users/${id}/editProfile`,{
            method:'POST',
            headers:{
                'Content-Type':'application/json',
            },
            body:JSON.stringify({
                name:newName,
                memo:newMemo,
            }),
        });
        if(response.ok){
        //完了アラートを出す
        alert('編集完了！');
        //テキストボックスを空欄にする
        setNewName('');
        setNewMemo('');
        //プロフィール画面に遷移
        navigate('/userProfile');
        }
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