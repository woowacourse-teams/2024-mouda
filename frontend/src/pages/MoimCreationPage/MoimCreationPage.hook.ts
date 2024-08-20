import useStatePersist from '@_hooks/useStatePersist';
import { MoimInputInfo } from '@_types/index';
import {
  isApprochedByUrl,
  validateDate,
  validateMaxPeople,
  validatePlace,
  validateTime,
  validateTitle,
} from './MoimCreatePage.util';
import { useEffect, useState } from 'react';
import useAddMoim from '@_hooks/mutaions/useAddMoim';
import { useNavigate, useNavigationType } from 'react-router-dom';
import { MoimCreationStep } from './MoimCreationPage';

const inputKeyMapper = {
  title: '이름입력',
  // offlineOrOnline: '오프라인/온라인선택',
  place: '장소선택',
  date: '날짜설정',
  time: '시간설정',
  maxPeople: '최대인원설정',
  description: '설명입력',
};

type MoimFormData = MoimInputInfo & {
  // offlineOrOnline: string;
  description: string;
};

const useMoimCreationForm = (currentStep: MoimCreationStep) => {
  const navigationType = useNavigationType();
  const navigate = useNavigate();

  const isNewMoimCreation =
    currentStep === '이름입력' &&
    (navigationType === 'PUSH' || isApprochedByUrl());

  if (isNewMoimCreation) {
    sessionStorage.removeItem('moimCreationInfo');
  }

  const [formData, setFormData] = useStatePersist<MoimFormData>({
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

  const [formValidation, setFormValidation] = useState({
    title: false,
    place: false,
    date: false,
    time: false,
    maxPeople: false,
  });

  const { mutate: createMoim } = useAddMoim((moimId: number) => {
    navigate(`/moim/${moimId}`);
  });

  useEffect(() => {
    const { title, date, time, place, maxPeople } = formData;
    setFormValidation({
      title: validateTitle(title),
      place: validatePlace(place),
      date: date === '' || validateDate(date),
      time: time === '' || validateTime(time),
      maxPeople: validateMaxPeople(maxPeople),
    });
  }, [formData]);

  const updateTitle = (title: string) => {
    setFormData((prev) => ({ ...prev, title }));
  };

  const updateOfflineOrOnline = (offlineOrOnline: string) => {
    setFormData((prev) => ({ ...prev, offlineOrOnline }));
  };

  const updatePlace = (place: string) => {
    setFormData((prev) => ({ ...prev, place }));
  };

  const updateDate = (date: string) => {
    setFormData((prev) => ({ ...prev, date }));
  };

  const updateTime = (time: string) => {
    setFormData((prev) => ({ ...prev, time }));
  };

  const updateMaxPeople = (maxPeople: number) => {
    setFormData((prev) => ({ ...prev, maxPeople }));
  };

  const updateDescription = (description: string) => {
    setFormData((prev) => ({ ...prev, description }));
  };

  const finalValidate = () => {
    const invalidInputKeys = Object.entries(formValidation)
      .filter(([, value]) => !value)
      .map(([key]) => key);

    if (invalidInputKeys.length > 0) {
      const invalidKeys = invalidInputKeys
        .map((key) => inputKeyMapper[key as keyof typeof inputKeyMapper])
        .join(', ');

      return {
        isValid: false,
        errorMessage: `${invalidKeys}이 올바르지 않습니다.`,
      };
    }

    return {
      isValid: true,
    };
  };

  return {
    formData,
    formValidation,
    updateTitle,
    updateOfflineOrOnline,
    updatePlace,
    updateDate,
    updateTime,
    updateMaxPeople,
    updateDescription,
    finalValidate,
    createMoim,
  };
};

export default useMoimCreationForm;
