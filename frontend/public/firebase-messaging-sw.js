/* eslint-disable no-undef */
importScripts(
  "https://www.gstatic.com/firebasejs/10.8.0/firebase-app-compat.js"
);
importScripts(
  "https://www.gstatic.com/firebasejs/10.8.0/firebase-messaging-compat.js"
);

importScripts('/firebaseConfig.js');

self.addEventListener("install", function () {
  self.skipWaiting();
});

self.addEventListener("activate", function () {
  console.log("fcm service worker가 실행되었습니다.");
});

self.addEventListener('notificationclick', function(event) {
  console.log('[firebase-messaging-sw.js] 알림이 클릭되었습니다.');

  // 알림 데이터를 가져오기
  const link = event.notification.data.FCM_MSG.notification.click_action;

  event.notification.close(); // 알림 닫기

  // 사용자가 알림을 클릭했을 때 해당 링크로 이동
  if (link) {
    event.waitUntil(
      clients.matchAll({ type: 'window', includeUncontrolled: true }).then(windowClients => {
        // 이미 열린 창이 있는지 확인
        for (let i = 0; i < windowClients.length; i++) {
          const client = windowClients[i];
          if (client.url === link && 'focus' in client) {
            return client.focus();
          }
        }
        // 새 창을 열거나 이미 있는 창으로 이동
        if (clients.openWindow) {
          return clients.openWindow(link);
        }
      })
    );
  }
});

firebase.initializeApp(firebaseConfig);

const messaging = firebase.messaging();

messaging.onBackgroundMessage((payload) => {
  console.log('[firebase-messaging-sw.js] 백그라운드 메시지 수신:', payload);
  
  const notificationTitle = payload.notification.title;
  const notificationOptions = {
    body: payload.notification.body,
    data: {
      click_action: payload.fcmOptions.link, // 이 필드는 밑의 클릭 이벤트 처리에 사용됨
    },
  };
  self.registration.showNotification(notificationTitle, notificationOptions);
});
