import Button from '@_components/Button/Button';
import FormLayout from '@_layouts/FormLayout/FormLayout';
import LabeledInput from '@_components/Input/MoimInput';
import usePleaseInfoInput from './PleaseCreationPage.hook';
import { useNavigate } from 'react-router-dom';
import { useState } from 'react';
import ROUTES from '@_constants/routes';
import PLEASE_INPUT_INFOS from './PleaseCreationPage.constant';
import useAddPlease from '@_hooks/mutaions/useAddPlease';

export default function MoimCreationPage() {
  const navigate = useNavigate();
  const [isSubmitted, setIsSubmitted] = useState(false);
  const { mutate } = useAddPlease(() => {
    navigate(-1);
  });

  const { inputData, handleChange, isValidMoimInfoInput } =
    usePleaseInfoInput();

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
      <FormLayout.Header onBackArrowClick={() => navigate(ROUTES.main)}>
        해주세요 만들기
      </FormLayout.Header>

      <FormLayout.MainForm>
        {PLEASE_INPUT_INFOS.map((info) => (
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
