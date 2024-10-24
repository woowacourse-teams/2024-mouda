/* eslint-disable compat/compat */

import { getMessaging, getToken } from 'firebase/messaging';
import { initializeFirebaseApp } from './initFirebase'; // 비동기적으로 초기화된 Firebase 앱 가져오기

export async function requestPermission(
  mutationFn: (currentToken: string) => void,
) {
  const app = await initializeFirebaseApp(); // Firebase 앱 초기화가 완료되면 실행
  if (!app) return; // Firebase를 사용할 수 없는 경우 종료

  const messaging = getMessaging(app);

  Notification.requestPermission().then((permission) => {
    if (permission === 'granted') {
      getToken(messaging, {
        vapidKey: process.env.VAPID_KEY,
      })
        .then((currentToken) => {
          if (currentToken) {
            mutationFn(currentToken);
          } else {
            console.warn(
              'No registration token available. Request permission to generate one.',
            );
          }
        })
        .catch((err) => {
          console.error('An error occurred while retrieving token: ', err);
        });
    } else {
      console.warn('알림 권한이 허용되지 않았습니다.');
    }
  });
}
