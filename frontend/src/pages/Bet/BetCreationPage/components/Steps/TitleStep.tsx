import FunnelButton from '@_components/Funnel/FunnelButton/FunnelButton';
import FunnelInput from '@_components/Funnel/FunnelInput/FunnelInput';
import FunnelQuestion from '@_components/Funnel/FunnelQuestion/FunnelQuestion';
import FunnelLayout from '@_layouts/FunnelLayout/FunnelLayout';
import { BetInputInfo } from '@_types/index';
import { useEffect, useRef } from 'react';

interface TitleProps {
  title: BetInputInfo['title'];
  isValid: boolean;
  onTitleChange: (title: string) => void;
  onButtonClick: () => void;
}

export default function TitleStep(props: TitleProps) {
  const { title, isValid, onTitleChange, onButtonClick } = props;

  const inputRef = useRef<HTMLInputElement>(null);

  useEffect(() => {
    if (inputRef.current) {
      inputRef.current.focus();
    }
  }, []);

  return (
    <>
      <FunnelLayout.Main>
        <FunnelQuestion>
          <FunnelQuestion.Text>무슨 </FunnelQuestion.Text>
          <FunnelQuestion.Highlight>내기</FunnelQuestion.Highlight>
          <FunnelQuestion.Text>를 할까요?</FunnelQuestion.Text>
        </FunnelQuestion>
        <FunnelInput
          ref={inputRef}
          placeholder="커피빵? 아이스크림빵?"
          value={title}
          onKeyUp={(e) => e.key === 'Enter' && isValid && onButtonClick()}
          onChange={(e) => onTitleChange(e.target.value)}
        />
      </FunnelLayout.Main>

      <FunnelLayout.Footer>
        <FunnelButton disabled={!isValid} onClick={onButtonClick}>
          {title === ''
            ? '제목을 입력해주세요'
            : !isValid
              ? '제목을 잘~ 입력해주세요'
              : '다음으로'}
        </FunnelButton>
      </FunnelLayout.Footer>
    </>
  );
}
