import { ChangeEvent, useState } from 'react';
import {
  validateDate,
  validateMaxPeople,
  validatePlace,
  validateTime,
  validateTitle,
} from './MoimModifyPage.util';

import { MoimInputInfo } from '../../../types';

const useMoimInfoInput = (state: MoimInputInfo) => {
  const [inputData, setInputData] = useState<MoimInputInfo>({
    title: state?.title ?? '',
    date: state?.date ?? '',
    time: state?.time ?? '',
    place: state?.place ?? '',
    maxPeople: state?.maxPeople ?? 0,
    description: state?.description ?? '',
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
    (inputData.time === '' || validateTime(inputData.time, inputData.date)) &&
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
