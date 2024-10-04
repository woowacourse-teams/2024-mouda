/* eslint-disable compat/compat */
import { getMessaging, getToken } from 'firebase/messaging';

import { app } from './initFirebase';
import checkCanUseFirebase from '@_utils/checkCanUseFirebase';

const messaging = checkCanUseFirebase() ? getMessaging(app) : null;

export function requestPermission(mutationFn: (currentToken: string) => void) {
  if (!checkCanUseFirebase()) return;
  console.log('권한 요청 중...');
  Notification.requestPermission().then((permission) => {
    if (permission === 'granted') {
      console.log('알림 권한이 허용됨');
      //@ts-expect-error 파이어베이스가 사용되면 messaging이 존재
      getToken(messaging, {
        vapidKey: process.env.VAPID_KEY,
      })
        .then((currentToken) => {
          if (currentToken) {
            console.log(currentToken);
            mutationFn(currentToken);
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
