
import type { JSX } from "react";
import { Navigate } from "react-router-dom";


const PrivateRoute = ({children}:{children:JSX.Element}) => {
    //ログインしていたらページ遷移、ログインしていない場合ログイン画面に戻る
    return <Navigate to='/' />;
}

export default PrivateRoute