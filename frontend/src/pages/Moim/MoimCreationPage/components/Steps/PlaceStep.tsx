import FunnelButton from '@_components/Funnel/FunnelButton/FunnelButton';
import FunnelInput from '@_components/Funnel/FunnelInput/FunnelInput';
import FunnelInputErrorMessage from '@_components/Funnel/FunnelInputErrorMessage/FunnelInputErrorMessage';
import FunnelQuestion from '@_components/Funnel/FunnelQuestion/FunnelQuestion';
import POLICES from '@_constants/poclies';
import FunnelLayout from '@_layouts/FunnelLayout/FunnelLayout';
import { useEffect, useRef } from 'react';

interface PlaceStepProps {
  // offlineOrOnline: string;
  place: string;
  isValid: boolean;
  onPlaceChange: (place: string) => void;
  onButtonClick: () => void;
}

export default function PlaceStep(props: PlaceStepProps) {
  const { place, isValid, onPlaceChange, onButtonClick } = props;

  const inputRef = useRef<HTMLInputElement>(null);

  useEffect(() => {
    if (inputRef.current) {
      inputRef.current.focus();
    }
  }, []);
  return (
    <>
      <FunnelLayout.Main>
        <FunnelQuestion id="moimPlaceLabel" htmlFor="moimPlaceInput">
          <FunnelQuestion.Highlight>장소명</FunnelQuestion.Highlight>
          <FunnelQuestion.Text>을</FunnelQuestion.Text>
          <br />
          <FunnelQuestion.Text>작성해주세요!</FunnelQuestion.Text>
        </FunnelQuestion>
        <FunnelInput
          id="moimPlaceInput"
          aria-labelledby="moimPlaceLabel"
          aria-describedby="moimPlaceDescription"
          ref={inputRef}
          value={place}
          onKeyUp={(e) => e.key === 'Enter' && isValid && onButtonClick()}
          onChange={(e) => onPlaceChange(e.target.value)}
          placeholder="주소를 입력해주세요"
        />
      </FunnelLayout.Main>

      <FunnelLayout.Footer>
        {!isValid && (
          <FunnelInputErrorMessage id="moimPlaceDescription">
            {POLICES.minimumPlaceLength} ~ {POLICES.maximumPlaceLength}글자
            이내로 입력해주세요 가능해요
          </FunnelInputErrorMessage>
        )}
        <FunnelButton disabled={!isValid} onClick={onButtonClick}>
          {place === '' ? '스킵하고 채팅에서 정할게요!' : '다음으로'}
        </FunnelButton>
      </FunnelLayout.Footer>
    </>
  );
}
