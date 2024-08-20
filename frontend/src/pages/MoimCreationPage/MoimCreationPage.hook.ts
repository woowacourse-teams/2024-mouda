import { useLocation, useNavigationType } from 'react-router-dom';
import { MoimCreationStep, steps } from './MoimCreationPage';
import useStatePersist from '@_hooks/useStatePersist';
import { MoimInputInfo } from '@_types/index';
import {
  validateDate,
  validateMaxPeople,
  validatePlace,
  validateTime,
  validateTitle,
} from './MoimCreatePage.util';
import { useEffect, useState } from 'react';

type MoimCreationInputInfo = MoimInputInfo & {
  // offlineOrOnline: string;
  description: string;
};

/**
 * @description 브라우저의 네비게이션 타입을 구분하는 함수.
 * 링크 클릭, URL 직접 입력으로 페이지에 들어온 경우를 구분하기 위해 사용.
 * react router로는 감지할 수 없어서 사용.
 * ref: https://developer.mozilla.org/en-US/docs/Web/API/PerformanceNavigationTiming/type
 */
const getBrowserNavigationType = ():
  | 'navigate'
  | 'reload'
  | 'back_forward'
  | 'prerender'
  | undefined => {
  const navEntries = performance.getEntriesByType('navigation');
  if (navEntries.length > 0) {
    const navEntry = navEntries[0] as PerformanceNavigationTiming;
    return navEntry.type;
  }
};

const isApprochedByUrl = () => {
  return getBrowserNavigationType() === 'navigate';
};

const useMoimCreationInfo = () => {
  const location = useLocation();
  const navigationType = useNavigationType();

  const currentStep: MoimCreationStep = location.state?.step || steps[0];

  const isStartOfMoimCreation =
    currentStep === '이름입력' &&
    (navigationType === 'PUSH' || isApprochedByUrl());

  if (isStartOfMoimCreation) {
    sessionStorage.removeItem('moimCreationInfo');
  }

  const [moimInfo, setMoimInfo] = useStatePersist<MoimCreationInputInfo>({
    key: 'moimCreationInfo',
    initialState: {
      title: '',
      // offlineOrOnline: '',
      place: '',
      date: '',
      time: '',
      maxPeople: 0,
      description: '',
    },
    storage: 'sessionStorage',
  });

  const [moimValidState, setMoimValidState] = useState({
    title: false,
    place: false,
    date: false,
    time: false,
    maxPeople: false,
  });

  useEffect(() => {
    const { title, date, time, place, maxPeople } = moimInfo;
    setMoimValidState({
      title: validateTitle(title),
      place: validatePlace(place),
      date: date === '' || validateDate(date),
      time: time === '' || validateTime(time),
      maxPeople: validateMaxPeople(maxPeople),
    });
  }, [moimInfo]);

  const handleTitleChange = (title: string) => {
    setMoimInfo((prev) => ({ ...prev, title }));
  };

  const handleOfflineOrOnlineChange = (offlineOrOnline: string) => {
    setMoimInfo((prev) => ({ ...prev, offlineOrOnline }));
  };

  const handlePlaceChange = (place: string) => {
    setMoimInfo((prev) => ({ ...prev, place }));
  };

  const handleDateChange = (date: string) => {
    setMoimInfo((prev) => ({ ...prev, date }));
  };

  const handleTimeChange = (time: string) => {
    setMoimInfo((prev) => ({ ...prev, time }));
  };

  const handleMaxPeopleChange = (maxPeople: number) => {
    setMoimInfo((prev) => ({ ...prev, maxPeople }));
  };

  const handleDescriptionChange = (description: string) => {
    setMoimInfo((prev) => ({ ...prev, description }));
  };

  return {
    currentStep,
    moimInfo,
    moimValidState,
    handleTitleChange,
    handleOfflineOrOnlineChange,
    handlePlaceChange,
    handleDateChange,
    handleTimeChange,
    handleMaxPeopleChange,
    handleDescriptionChange,
  };
};

export default useMoimCreationInfo;
