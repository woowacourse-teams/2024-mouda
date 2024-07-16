import { ChangeEvent, useMemo, useState } from 'react';

import Button from '../components/Button/Button';
import ENDPOINTS from '../constants/endpoints';
import FormLayout from '../layouts/FormLayout/FormLayout';
import { Input_Info_List } from '../constants';
import { MoimInfo } from '../types/requests';
import MoimInput from '../components/Input/MoimInput';
import useAddMoim from '../mutaions/useAddMoim';
import { useNavigate } from 'react-router-dom';

export default function MoimRegisterPage() {
  const navigate = useNavigate();
  const { mutate } = useAddMoim(() => navigate(ENDPOINTS.main));
  const [isSubmitted, setIsSubmitted] = useState(false);

  const [inputData, setInputData] = useState<MoimInfo>({
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

  const validateTitle = (title: string) =>
    title.length > 0 && title.length <= 20;
  const validateDate = (date: string) => {
    const nowDate = new Date();
    const nowDateYyyymmdd = `${nowDate.getFullYear()}-${(nowDate.getMonth() + 1).toString().padStart(2, '00')}-${nowDate.getDate().toString().padStart(2, '00')}`;
    return (
      date >= nowDateYyyymmdd &&
      /^\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12]\d|3[01])$/.test(date)
    );
  };
  const validateTime = (time: string) => /^\d{2}:\d{2}$/.test(time);
  const validatePlace = (place: string) =>
    place.length > 0 && place.length <= 100;
  const validateMaxPeople = (maxPeople: number) =>
    maxPeople >= 1 && maxPeople <= 99;
  const validateAuthorNickname = (authorNickname: string) =>
    authorNickname.length > 0 && authorNickname.length <= 10;

  const isValidate = useMemo(() => {
    console.log(
      validateTitle(inputData.title),
      validateDate(inputData.date),
      validateTime(inputData.time),
      validatePlace(inputData.place),
      validateMaxPeople(inputData.maxPeople),
      validateAuthorNickname(inputData.authorNickname),
    );
    return !(
      validateTitle(inputData.title) &&
      validateDate(inputData.date) &&
      validateTime(inputData.time) &&
      validatePlace(inputData.place) &&
      validateMaxPeople(inputData.maxPeople) &&
      validateAuthorNickname(inputData.authorNickname)
    );
  }, [inputData]);

  const handleRegisterButtonClick = async () => {
    if (
      inputData.title === '' ||
      inputData.date === '' ||
      inputData.time === '' ||
      inputData.place === '' ||
      inputData.maxPeople <= 0 ||
      inputData.authorNickname === ''
    ) {
      return;
    }
    if (isSubmitted) return;
    setIsSubmitted(true);
    mutate(inputData);
  };

  return (
    <FormLayout>
      <FormLayout.Header onBackArrowClick={() => navigate(ENDPOINTS.main)}>
        모임등록하기
      </FormLayout.Header>

      <FormLayout.MainForm>
        {Object.entries(Input_Info_List).map(([key, info]) => (
          <MoimInput key={key} data={info} name={key} onChange={handleChange} />
        ))}
      </FormLayout.MainForm>

      <FormLayout.BottomButtonWrapper>
        <Button
          shape="bar"
          onClick={handleRegisterButtonClick}
          disabled={isValidate}
        >
          등록하기
        </Button>
      </FormLayout.BottomButtonWrapper>
    </FormLayout>
  );
}
