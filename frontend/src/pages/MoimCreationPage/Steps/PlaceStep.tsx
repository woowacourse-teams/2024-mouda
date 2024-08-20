import FunnelButton from '@_components/Funnel/FunnelButton/FunnelButton';
import FunnelInput from '@_components/Funnel/FunnelInput/FunnelInput';
import FunnelQuestion from '@_components/Funnel/FunnelQuestion/FunnelQuestion';
import POLICES from '@_constants/poclies';
import FunnelLayout from '@_layouts/FunnelLayout/FunnelLayout';

interface PlaceStepProps {
  // offlineOrOnline: string;
  place: string;
  isValid: boolean;
  onPlaceChange: (place: string) => void;
  onButtonClick: () => void;
}

export default function PlaceStep(props: PlaceStepProps) {
  const { place, isValid, onPlaceChange, onButtonClick } = props;

  // if (offlineOrOnline === 'online') {
  //   return (
  //     <>
  //       <FunnelQuestion>
  //         <FunnelQuestion.Highlight>온라인 링크</FunnelQuestion.Highlight>
  //         <FunnelQuestion.Text>를</FunnelQuestion.Text>
  //         <br />
  //         <FunnelQuestion.Text>작성해주세요!</FunnelQuestion.Text>
  //       </FunnelQuestion>
  //       <FunnelInput
  //         value={place}
  //         onChange={(e) => onPlaceChange(e.target.value)}
  //         placeholder="ex. URL, 칼바람 나락 ..."
  //       />
  //     </>
  //   );
  // }

  return (
    <>
      <FunnelLayout.Main>
        <FunnelQuestion>
          <FunnelQuestion.Highlight>장소명</FunnelQuestion.Highlight>
          <FunnelQuestion.Text>을</FunnelQuestion.Text>
          <br />
          <FunnelQuestion.Text>작성해주세요!</FunnelQuestion.Text>
        </FunnelQuestion>
        <FunnelInput
          value={place}
          onChange={(e) => onPlaceChange(e.target.value)}
          placeholder="주소를 입력해주세요"
        />
      </FunnelLayout.Main>

      <FunnelLayout.Footer>
        <FunnelButton disabled={!isValid} onClick={onButtonClick}>
          {place === ''
            ? '스킵하고 채팅에서 정할게요!'
            : !isValid
              ? `${POLICES.minimumPlaceLength} ~ ${POLICES.maximumPlaceLength}글자만 가능해요`
              : '다음으로'}
        </FunnelButton>
      </FunnelLayout.Footer>
    </>
  );
}
