import checkCanUseFirebase from '@_utils/checkCanUseFirebase';
import { initializeApp } from 'firebase/app';

const firebaseConfig = {
  apiKey: process.env.REACT_APP_FIREBASE_API_KEY,
  authDomain: process.env.REACT_APP_FIREBASE_AUTH_DOMAIN,
  projectId: process.env.REACT_APP_FIREBASE_PROJECT_ID,
  storageBucket: process.env.REACT_APP_FIREBASE_STORAGE_BUCKET,
  messagingSenderId: process.env.REACT_APP_FIREBASE_MESSAGING_SENDER_ID,
  appId: process.env.REACT_APP_FIREBASE_APP_ID,
  measurementId: process.env.REACT_APP_FIREBASE_MEASUREMENT_ID,
};

export const initializeFirebaseApp = async () => {
  const canUseFirebase = await checkCanUseFirebase();
  if (canUseFirebase) {
    return initializeApp(firebaseConfig);
  } else {
    console.warn('Firebase는 이 환경에서 지원되지 않습니다.');
    return undefined;
  }
};
