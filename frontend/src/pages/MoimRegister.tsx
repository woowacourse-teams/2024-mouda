import { useNavigate } from 'react-router-dom';
import Button from '../components/Button/Button';
import MoimInput from '../components/Input/MoimInput';
import { Input_Info_List } from '../constants';
import FormLayout from '../layouts/FormLayout/FormLayout';
import ENDPOINTS from '../constants/endpoints';
import { ChangeEvent, useState } from 'react';
import { MoimInfo } from '../types/requests';
import useAddMoim from '../mutaions/useAddMoim';

export default function MoimRegisterPage() {
  const navigate = useNavigate();
  const { mutate } = useAddMoim(() => navigate(ENDPOINTS.main));

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
        <Button shape="bar" onClick={handleRegisterButtonClick}>
          등록하기
        </Button>
      </FormLayout.BottomButtonWrapper>
    </FormLayout>
  );
}
