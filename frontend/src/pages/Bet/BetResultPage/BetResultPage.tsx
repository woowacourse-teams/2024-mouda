import * as S from './BetResultPage.style';

import { MutableRefObject, useEffect, useMemo, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';

import BackArrowButton from '@_components/Button/BackArrowButton/BackArrowButton';
import Button from '@_components/Button/Button';
import GET_ROUTES from '@_common/getRoutes';
import Roulette from '../components/Roulette/Roulette';
import RouletteWrapper from '../components/RouletteWrapper/RouletteWrapper';
import SelectLayout from '@_layouts/SelectLayout/SelectLayout';
import StickyTriSectionHeader from '@_layouts/components/StickyTriSectionHeader/StickyTriSectionHeader';
import useBet from '@_hooks/queries/useBet';
import useBetResult from '@_hooks/queries/useBetResult';
import { useTheme } from '@emotion/react';

// import StarFourIcon from '@_components/Icons/StarIcons/StarFourIcon';
// import StarOneIcon from '@_components/Icons/StarIcons/StarOneIcon';
// import StarThreeIcon from '@_components/Icons/StarIcons/StarThreeIcon';
// import StarTwoIcon from '@_components/Icons/StarIcons/StarTwoIcon';

export default function BetResultPage() {
  const navigate = useNavigate();

  const theme = useTheme();
  const params = useParams();
  const betId = Number(params.betId);

  const { bet } = useBet(betId);
  const { betResult } = useBetResult(betId);
  const nameList = useMemo(
    () => bet?.participants.map(({ nickname }) => nickname),
    [bet?.participants],
  );

  const [isRouletteEnd, setIsRouletteEnd] = useState(false);
  const [isButtonShown, setIsButtonShown] = useState(false);

  useEffect(() => {
    document.body.style.backgroundColor = theme.colorPalette.orange[200];
    document.body.style.transition = '1s all ease-in-out';
  }, [theme]);

  const onEffect = (ref: MutableRefObject<null | HTMLDivElement>) => {
    if (ref.current) {
      ref.current.style.backgroundColor = theme.colorPalette.orange[200];
      ref.current.style.transition = '1s all ease-in-out';
    }
  };

  const afterEffect = (ref: MutableRefObject<null | HTMLDivElement>) => {
    if (ref.current) {
      ref.current.style.backgroundColor = '';
      ref.current.style.transition = '';
    }
  };

  const handleAfterRoulette = () => {
    setTimeout(() => {
      setIsRouletteEnd(true);
    }, 300);
    setTimeout(() => {
      setIsButtonShown(true);
    }, 1100);
  };
  if (bet?.isAnnounced === false) {
    alert('아직 발표되지 않은 안내면 진다입니다.');
    navigate(GET_ROUTES.nowDarakbang.bet());
    return null;
  }

  return (
    <SelectLayout>
      <StickyTriSectionHeader onEffect={onEffect} afterEffect={afterEffect}>
        <StickyTriSectionHeader.Left>
          <div onClick={() => navigate(-1)}>
            <BackArrowButton />
          </div>
        </StickyTriSectionHeader.Left>
      </StickyTriSectionHeader>

      <SelectLayout.ContentContainer>
        <div css={S.containerStyle}>
          <RouletteWrapper
            title={bet?.title || ''}
            description={`지금 당첨될 확률은 *${((1 / (bet?.participants.length || 1)) * 100).toFixed(1)}*%!`}
            mainDescription={isRouletteEnd ? betResult + ' 당첨!!' : ''}
          >
            {nameList && (
              <Roulette
                nameList={nameList}
                loser={betResult}
                startSpeed={10}
                minMs={3000}
                itemPercent={120}
                stopSpeed={4}
                onEnd={handleAfterRoulette}
              />
            )}
          </RouletteWrapper>
          {isButtonShown && (
            <Button shape="bar" reversePrimary>
              채팅방으로 가기
            </Button>
          )}
        </div>
      </SelectLayout.ContentContainer>
    </SelectLayout>
  );
}

// 별이 움직이는 컴포넌트
// <div css={containerStyle}>
//         <div css={starContainerStyle}>
//           <div css={theme.typography.s1}>{betResult} !</div>

//           <div css={[starOneStyle, rotateAnimation]}>
//             <StarOneIcon />
//           </div>
//           <div css={[starTwoStyle, scaleAnimation]}>
//             <StarThreeIcon />
//           </div>
//           <div css={[starThreeStyle, scaleAnimation]}>
//             <StarFourIcon />
//           </div>
//           <div css={[starFourStyle, rotateAnimation]}>
//             <StarTwoIcon />
//           </div>
//         </div>
//       </div>
// const containerStyle = css`
//   display: flex;
//   align-items: center;
//   justify-content: center;
//   height: 100vh;
// `;

// const starContainerStyle = css`
//   position: relative;

//   display: flex;
//   align-items: center;
//   justify-content: center;

//   width: 150px;
//   height: 50px;
// `;

// const starOneStyle = css`
//   position: absolute;
//   top: -30px;
//   left: -40px;
// `;

// const starTwoStyle = css`
//   position: absolute;
//   top: -20px;
//   right: -30px;
// `;

// const starThreeStyle = css`
//   position: absolute;
//   bottom: -20px;
//   left: -20px;
// `;

// const starFourStyle = css`
//   position: absolute;
//   right: -20px;
//   bottom: -30px;
// `;

// const rotate = keyframes`
//   0% { transform: rotate(0deg); }
//   50% { transform: rotate(15deg); }
//   100% { transform: rotate(0deg); }
// `;

// const scale = keyframes`
//   0%, 100% { transform: scale(1); }
//   50% { transform: scale(1.2); }
// `;

// const rotateAnimation = css`
//   animation: ${rotate} 2s infinite ease-in-out;
// `;

// const scaleAnimation = css`
//   animation: ${scale} 2s infinite ease-in-out;
// `;
