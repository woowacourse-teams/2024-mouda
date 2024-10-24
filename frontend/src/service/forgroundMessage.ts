import { getMessaging, onMessage } from 'firebase/messaging';
import { initializeFirebaseApp } from './initFirebase';

async function initializeForegroundMessageHandling() {
  const app = await initializeFirebaseApp(); // Firebase 앱 초기화가 완료되면 실행
  if (!app) return; // Firebase를 사용할 수 없는 경우 종료

  const messaging = getMessaging(app);

  onMessage(messaging, (payload) => {
    const notificationTitle = payload.notification?.title || '알림';
    const notificationOptions = {
      body: payload.notification?.body || '',
      icon: payload.notification?.icon,
      data: { link: payload.data?.link || '/' },
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

if ('serviceWorker' in navigator && process.env.MSW !== 'true') {
  navigator.serviceWorker
    .register(`/firebase-messaging-sw.js`)
    .then(() => {
      initializeForegroundMessageHandling();
    })
    .catch(() => {
      // console.log('Service Worker registration failed:', error);
    });
} else {
  // 서비스 워커가 지원되지 않는 경우에도 포그라운드 메시지 처리를 초기화
  initializeForegroundMessageHandling();
}
