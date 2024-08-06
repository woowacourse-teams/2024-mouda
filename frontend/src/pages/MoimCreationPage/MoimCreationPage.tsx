import Button from '@_components/Button/Button';
import FormLayout from '@_layouts/FormLayout/FormLayout';
import LabeledInput from '@_components/Input/MoimInput';
import MOIM_INPUT_INFOS from './MoimCreationPage.constant';
import useAddMoim from '@_hooks/mutaions/useAddMoim';
import useMoimInfoInput from './MoimCreatePage.hook';
import { useNavigate } from 'react-router-dom';
import { useState } from 'react';
import ROUTES from '@_constants/routes';
import * as S from './MoimCreationPage.style';
import LabeledTextArea from '@_components/TextArea/LabeledTextArea';

export default function MoimCreationPage() {
  const navigate = useNavigate();
  const { mutate } = useAddMoim((moimId: number) => {
    navigate(`/moim/${moimId}`);
  });
  const [isSubmitted, setIsSubmitted] = useState(false);

  const {
    inputData,
    handleInputChange,
    handleTextAreaChange,
    isValidMoimInfoInput,
  } = useMoimInfoInput();

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
        모임등록하기
      </FormLayout.Header>

      <FormLayout.MainForm>
        <div css={S.inputContainer}>
          {MOIM_INPUT_INFOS.map((info) =>
            info.type === 'textarea' ? (
              <LabeledTextArea
                name={info.name}
                title={info.title}
                key={info.title}
                required={info.required}
                onChange={handleTextAreaChange}
              />
            ) : (
              <LabeledInput
                name={info.name}
                title={info.title}
                key={info.title}
                required={info.required}
                onChange={handleInputChange}
              />
            ),
          )}
        </div>
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
