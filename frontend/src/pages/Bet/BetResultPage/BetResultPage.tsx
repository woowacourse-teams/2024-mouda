import BackArrowButton from '@_components/Button/BackArrowButton/BackArrowButton';
import StarFourIcon from '@_components/Icons/StarIcons/StarFourIcon';
import StarOneIcon from '@_components/Icons/StarIcons/StarOneIcon';
import StarThreeIcon from '@_components/Icons/StarIcons/StarThreeIcon';
import StarTwoIcon from '@_components/Icons/StarIcons/StarTwoIcon';
import useBetResult from '@_hooks/queries/useBetResult';
import StickyTriSectionHeader from '@_layouts/components/StickyTriSectionHeader/StickyTriSectionHeader';
import { css, keyframes, useTheme } from '@emotion/react';
import { useNavigate, useParams } from 'react-router-dom';

// TODO: 추후 삭제 예정 (당첨 결과는 채팅에서 확인할 수 있도록 변경 예정)
export default function BetResultPage() {
  const navigate = useNavigate();
  const params = useParams();
  const betId = Number(params.betId);

  const { betResult } = useBetResult(betId);

  const theme = useTheme();

  return (
    <>
      <StickyTriSectionHeader>
        <StickyTriSectionHeader.Left>
          <div onClick={() => navigate(-1)}>
            <BackArrowButton />
          </div>
        </StickyTriSectionHeader.Left>
      </StickyTriSectionHeader>

      <div css={containerStyle}>
        <div css={starContainerStyle}>
          <div css={theme.typography.s1}>{betResult} !</div>

          <div css={[starOneStyle, rotateAnimation]}>
            <StarOneIcon />
          </div>
          <div css={[starTwoStyle, scaleAnimation]}>
            <StarThreeIcon />
          </div>
          <div css={[starThreeStyle, scaleAnimation]}>
            <StarFourIcon />
          </div>
          <div css={[starFourStyle, rotateAnimation]}>
            <StarTwoIcon />
          </div>
        </div>
      </div>
    </>
  );
}

const containerStyle = css`
  display: flex;
  align-items: center;
  justify-content: center;
  height: 100vh;
`;

const starContainerStyle = css`
  position: relative;

  display: flex;
  align-items: center;
  justify-content: center;

  width: 150px;
  height: 50px;
`;

const starOneStyle = css`
  position: absolute;
  top: -30px;
  left: -40px;
`;

const starTwoStyle = css`
  position: absolute;
  top: -20px;
  right: -30px;
`;

const starThreeStyle = css`
  position: absolute;
  bottom: -20px;
  left: -20px;
`;

const starFourStyle = css`
  position: absolute;
  right: -20px;
  bottom: -30px;
`;

const rotate = keyframes`
  0% { transform: rotate(0deg); }
  50% { transform: rotate(15deg); }
  100% { transform: rotate(0deg); }
`;

const scale = keyframes`
  0%, 100% { transform: scale(1); }
  50% { transform: scale(1.2); }
`;

const rotateAnimation = css`
  animation: ${rotate} 2s infinite ease-in-out;
`;

const scaleAnimation = css`
  animation: ${scale} 2s infinite ease-in-out;
`;
