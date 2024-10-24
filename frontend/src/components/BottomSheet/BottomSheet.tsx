import Dimmer from '@_components/Dimmer/Dimmer';
import { PropsWithChildren, useEffect, useState } from 'react';
import BottomSheetContainer from './BottomSheetContainer/BottomSheetContainer';
import BottomSheetBody from './BottomSheetBody/BottomSheetBody';
import BottomSheetHandle from './BottomSheetHandle/BottomSheetHandle';

interface BottomSheetProps {
  isOpen: boolean;
  onDimmerClick: () => void;

  header?: React.ReactNode;
  cta?: React.ReactNode;

  size?: 'small' | 'medium' | 'large' | 'full';
}

export default function BottomSheet(
  props: PropsWithChildren<BottomSheetProps>,
) {
  const { isOpen, onDimmerClick, header, cta, children, size } = props;

  const [startY, setStartY] = useState(0); // 터치 시작 Y좌표
  const [currentY, setCurrentY] = useState(window.innerHeight); // 초기에는 화면 아래에 위치
  const [isDragging, setIsDragging] = useState(false); // 드래그 중인지 여부
  const [isClosing, setIsClosing] = useState(false); // 닫히는 애니메이션 여부

  // Bottom Sheet가 열릴 때 애니메이션으로 위로 올라옴
  useEffect(() => {
    if (isOpen) {
      document.body.style.overflow = 'hidden'; // 스크롤 비활성화

      // 열릴 때 100px 아래에서 0으로 부드럽게 올라오도록 애니메이션 시작
      setTimeout(() => {
        setCurrentY(0); // Y값을 0으로 변경 -> 밑에서 위로 올라오는 애니메이션
      }, 10); // 딜레이를 줘야 애니메이션이 자연스럽게 동작
    } else {
      setCurrentY(window.innerHeight); // 닫힐 때 다시 화면 아래로
      document.body.style.overflow = 'auto'; // 스크롤 활성화
    }

    return () => {
      document.body.style.overflow = 'auto';
    };
  }, [isOpen]);

  useEffect(() => {
    if (!isOpen) {
      setCurrentY(window.innerHeight); // 닫힐 때 높이를 초기화
      setIsClosing(false); // 닫힘 애니메이션 초기화
    }
  }, [isOpen]);

  // Dimmer 클릭 시 애니메이션으로 밑으로 내려간 후 닫힘
  const handleDimmerClick = () => {
    setIsClosing(true);
    setCurrentY(window.innerHeight); // 화면 아래로 내려가는 애니메이션
    setTimeout(() => {
      onDimmerClick(); // 300ms 후 실제로 닫기
    }, 300); // 애니메이션 시간이 0.3초이므로 300ms 후에 닫음
  };

  // 드래그 시작
  const handleTouchStart = (event: React.TouchEvent) => {
    setStartY(event.touches[0].clientY);
    setIsDragging(true);
  };

  // 드래그 중
  const handleTouchMove = (event: React.TouchEvent) => {
    if (!isDragging) return;

    const currentTouchY = event.touches[0].clientY;
    const deltaY = currentTouchY - startY;

    // Y축으로만 움직임을 감지
    if (deltaY > 0) {
      setCurrentY(deltaY); // 드래그된 만큼 값을 저장
    }
  };

  // 드래그 종료
  const handleTouchEnd = () => {
    if (!isDragging) return;
    setIsDragging(false);

    // 드래그가 일정 값 이상이면 Bottom Sheet를 닫음
    if (currentY > 100) {
      // 애니메이션으로 Bottom Sheet를 아래로 내리고 닫기
      setIsClosing(true);
      setCurrentY(window.innerHeight); // 화면 하단으로 내리는 애니메이션
      setTimeout(() => {
        onDimmerClick(); // 애니메이션 후 실제로 닫기
      }, 300); // 애니메이션 시간이 0.3초이므로 300ms 후에 닫음
    } else {
      setCurrentY(0); // 원래 위치로 되돌림
    }
  };

  if (!isOpen && !isClosing) {
    return null;
  }

  return (
    <BottomSheetContainer>
      <Dimmer onClick={handleDimmerClick} />
      <BottomSheetBody currentY={currentY} size={size} isDragging={isDragging}>
        <BottomSheetHandle
          onTouchStart={handleTouchStart}
          onTouchMove={handleTouchMove}
          onTouchEnd={handleTouchEnd}
        />
        {header}
        {children}
        {cta}
      </BottomSheetBody>
    </BottomSheetContainer>
  );
}

BottomSheet.Header = function BottomSheetHeader(props: PropsWithChildren) {
  const { children } = props;

  return <div css={{ padding: '0 24px' }}>{children}</div>;
};

BottomSheet.Main = function BottomSheetMain(props: PropsWithChildren) {
  const { children } = props;

  return <div css={{ padding: '0px 24px' }}>{children}</div>;
};

BottomSheet.CTA = function BottomSheetCTA(props: PropsWithChildren) {
  const { children } = props;

  return <div css={{ padding: '0px 24px' }}>{children}</div>;
};
