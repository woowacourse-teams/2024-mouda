import { ChangeEvent, useState } from 'react';
import {
  validateAuthorNickname,
  validateDate,
  validateMaxPeople,
  validatePlace,
  validateTime,
  validateTitle,
} from './MoimCreatePage.util';

import { MoimInputInfo } from '../../types';

const useMoimInfoInput = () => {
  const [inputData, setInputData] = useState<MoimInputInfo>({
    title: '',
    date: '',
    time: '',
    place: '',
    maxPeople: 0,
    authorNickname: '',
    description: '',
  });

  const handleChange = (e: ChangeEvent<HTMLInputElement>) => {
    setInputData({
      ...inputData,
      [e.target.name]: e.target.value,
    });
  };

  const isValidMoimInfoInput =
    validateTitle(inputData.title) &&
    validateDate(inputData.date) &&
    validateTime(inputData.time) &&
    validatePlace(inputData.place) &&
    validateMaxPeople(inputData.maxPeople) &&
    validateAuthorNickname(inputData.authorNickname);

  return {
    inputData,
    handleChange,
    isValidMoimInfoInput,
  };
};

export default useMoimInfoInput;
