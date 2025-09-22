import { Client } from "@stomp/stompjs";
import { useEffect } from "react";
import SockJS from "sockjs-client";

const useShareSubscribe = (userId:string,onMessage:(msg:any)=>void) => {
    useEffect(()=>{
        if(!userId) return;
        const socket = new SockJS("http://localhost:8080/ws");
        const client = new Client({
            webSocketFactory:()=>socket,
            reconnectDelay:5000,
            onConnect:()=>{
                console.log("WebSocket Connected!");
                client.subscribe(`/topic/share/${userId}`,(message)=>{
                    console.log("Receiuved Message:",message.body);
                    try{
                        onMessage(JSON.parse(message.body))
                    }catch{
                        onMessage(message.body);
                    }
                })
            },
        });
        client.activate();
        return () =>{
            client.deactivate();
        };
    },[userId,onMessage]);
}

export default useShareSubscribe