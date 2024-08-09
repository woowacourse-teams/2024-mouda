import { ChangeEvent, useState } from 'react';
import {
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
    description: '',
  });

  const handleInputChange = (e: ChangeEvent<HTMLInputElement>) => {
    setInputData({
      ...inputData,
      [e.target.name]: e.target.value,
    });
  };

  const handleTextAreaChange = (e: ChangeEvent<HTMLTextAreaElement>) => {
    setInputData({
      ...inputData,
      [e.target.name]: e.target.value,
    });
  };

  const isValidMoimInfoInput =
    validateTitle(inputData.title) &&
    (inputData.date === '' || validateDate(inputData.date)) &&
    (inputData.time === '' || validateTime(inputData.time)) &&
    (inputData.place === '' || validatePlace(inputData.place)) &&
    validateMaxPeople(inputData.maxPeople);

  return {
    inputData,
    handleInputChange,
    handleTextAreaChange,
    isValidMoimInfoInput,
  };
};

export default useMoimInfoInput;
