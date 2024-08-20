import { getMessaging, onMessage } from 'firebase/messaging';
import { app } from './initFirebase';

function initializeForegroundMessageHandling() {
  const messaging = getMessaging(app);

  onMessage(messaging, (payload) => {
    console.log('포그라운드 알림 도착: ', payload);

    const notificationTitle = payload.notification?.title || '알림';
    const notificationOptions = {
      body: payload.notification?.body || '',
      icon: payload.notification?.icon,
      data: { link: payload.fcmOptions?.link || '/' },
    };

    if (Notification.permission === 'granted') {
      try {
        const notification = new Notification(
          notificationTitle,
          notificationOptions,
        );

        notification.onclick = function (event) {
          event.preventDefault();
          window.open(notificationOptions.data.link, '_blank');
        };
      } catch (error) {
        console.error('알림 생성 중 오류 발생:', error);
      }
    } else {
      console.warn('알림 권한이 허용되지 않았습니다.');
    }
  });
}

if ('serviceWorker' in navigator) {
  navigator.serviceWorker
    .register(`/firebase-messaging-sw.js`)
    .then((registration) => {
      console.log('Service Worker registered with scope:', registration.scope);
      initializeForegroundMessageHandling();
    })
    .catch((error) => {
      console.log('Service Worker registration failed:', error);
    });
} else {
  // 서비스 워커가 지원되지 않는 경우에도 포그라운드 메시지 처리를 초기화
  initializeForegroundMessageHandling();
}
