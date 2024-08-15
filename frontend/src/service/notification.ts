import { getMessaging, getToken } from 'firebase/messaging';
import { app } from './initFirebase';

const messaging = getMessaging(app);

export function requestPermission() {
  console.log('권한 요청 중...');
  Notification.requestPermission().then((permission) => {
    if (permission === 'granted') {
      console.log('알림 권한이 허용됨');
      getToken(messaging, {
        vapidKey: process.env.VAPID_KEY,
      })
        .then((currentToken) => {
          if (currentToken) {
            console.log(currentToken);
          } else {
            // Show permission request UI
            console.log(
              'No registration token available. Request permission to generate one.',
            );
            // ...
          }
        })
        .catch((err) => {
          console.log('An error occurred while retrieving token. ', err);
          // ...
        });
      // FCM 메세지 처리
    } else {
      console.log('알림 권한 허용 안됨');
    }
  });
}
