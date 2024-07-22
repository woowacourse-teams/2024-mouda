import Button from '@_components/Button/Button';
import FormLayout from '@_layouts/FormLayout/FormLayout';
import LabeledInput from '@_components/Input/MoimInput';
import MOIM_INPUT_INFOS from './MoimCreationPage.constant';
import useAddMoim from '@_hooks/mutaions/useAddMoim';
import useMoimInfoInput from './MoimCreatePage.hook';
import { useNavigate } from 'react-router-dom';
import { useState } from 'react';

export default function MoimCreationPage() {
  const navigate = useNavigate();
  const { mutate } = useAddMoim((moimId: number) => {
    navigate(`/${moimId}`);
  });
  const [isSubmitted, setIsSubmitted] = useState(false);

  const { inputData, handleChange, isValidMoimInfoInput } = useMoimInfoInput();

  const handleRegisterButtonClick = async () => {
    if (!isValidMoimInfoInput) {
      return;
    }
    if (isSubmitted) return;
    setIsSubmitted(true);
    mutate(inputData);
  };

  return (
    <FormLayout>
      <FormLayout.Header onBackArrowClick={() => navigate(-1)}>
        모임등록하기
      </FormLayout.Header>

      <FormLayout.MainForm>
        {MOIM_INPUT_INFOS.map((info) => (
          <LabeledInput {...info} key={info.title} onChange={handleChange} />
        ))}
      </FormLayout.MainForm>

      <FormLayout.BottomButtonWrapper>
        <Button
          shape="bar"
          onClick={handleRegisterButtonClick}
          disabled={!isValidMoimInfoInput}
        >
          등록하기
        </Button>
      </FormLayout.BottomButtonWrapper>
    </FormLayout>
  );
}
