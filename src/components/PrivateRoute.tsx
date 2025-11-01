
import type { JSX } from "react";
import { Navigate } from "react-router-dom";


const PrivateRoute = ({children}:{children:JSX.Element}) => {
    //ログインしていたらページ遷移、ログインしていない場合ログイン画面に戻る
    const token = localStorage.getItem("token");
    if(!token){
        return <Navigate to='/' replace />;
    }
    return children;
}

export default PrivateRoute