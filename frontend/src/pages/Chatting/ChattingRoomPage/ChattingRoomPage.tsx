import { css, useTheme } from '@emotion/react';
import { useMemo, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';

import CalenderClock from '@_components/Icons/CalenderClock';
import ChatBottomMenu from './components/ChatBottomMenu/ChatBottomMenu';
import ChatList from './components/ChatList/ChatList';
import ChatMenuItem from './components/ChatMenuItem/ChatMenuItem';
import ChattingFooter from './components/ChattingFooter/ChattingFooter';
import ChattingRoomLayout from '@_layouts/ChattingRoomLayout/ChattingRoomLayout';
import ChattingRoomSidebar from './components/ChattingRoomSidebar/ChattingRoomSidebar';
import DarakbangNameWrapper from '@_components/DarakbangNameWrapper/DarakbangNameWrapper';
import DateTimeModalContent from './components/DateTimeModalContent/DateTimeModalContent';
import Hamburger from '@_components/Icons/Hamburger';
import MissingFallback from '@_components/MissingFallback/MissingFallback';
import Modal from '@_components/Modal/Modal';
import MoimInfoAccordion from './components/MoimInfoAccordion/MoimInfoAccordion';
import Picker from '@_components/Icons/Picker';
import PlaceModalContent from './components/PlaceModalContent/PlaceModalContent';
import SolidArrow from '@_components/Icons/SolidArrow';
import useChamyoAll from '@_hooks/queries/useChamyoAll';
import useChamyoMine from '@_hooks/queries/useChamyoMine';
import useChats from '@_hooks/queries/useChats';
import useConfirmDateTime from '@_hooks/mutaions/useConfirmDatetime';
import useConfirmPlace from '@_hooks/mutaions/useConfirmPlace';
import useMoim from '@_hooks/queries/useMoim';
import useSendMessage from '@_hooks/mutaions/useSendMessage';

type ModalContent = 'place' | 'datetime';

export default function ChattingRoomPage() {
  const theme = useTheme();
  const params = useParams();
  const navigate = useNavigate();

  const moimId = +(params.moimId || '0');

  const { chats } = useChats(moimId);
  const { role } = useChamyoMine(moimId);
  const { moim } = useMoim(moimId);
  const { participants } = useChamyoAll(moimId);

  const { mutate: confirmDateTime, isPending: isPendingConfirmDateTime } =
    useConfirmDateTime();
  const { mutate: confirmPlace, isPending: isPendingConfirmPlacd } =
    useConfirmPlace();
  const { mutate: handleSendMessage, isPending: isPendingSendMessage } =
    useSendMessage(moimId);
  const [isMenuOpen, setIsMenuOpen] = useState(false);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [isSidebarOpen, setIsSidebarOpen] = useState(false);
  const [nowModalContent, setNowModalContent] = useState<ModalContent>('place');

  const modal = useMemo(() => {
    if (nowModalContent === 'datetime')
      return (
        !isPendingConfirmDateTime && (
          <Modal onClose={() => setIsModalOpen(false)}>
            <DateTimeModalContent
              onCancel={() => setIsModalOpen(false)}
              onConfirm={({ date, time }: { date: string; time: string }) => {
                confirmDateTime({ moimId, date, time });
                setIsModalOpen(false);
              }}
            />
          </Modal>
        )
      );
    if (nowModalContent === 'place')
      return (
        !isPendingConfirmPlacd && (
          <Modal onClose={() => setIsModalOpen(false)}>
            <PlaceModalContent
              onCancel={() => setIsModalOpen(false)}
              onConfirm={(place: string) => {
                confirmPlace({ moimId, place });
                setIsModalOpen(false);
              }}
            />
          </Modal>
        )
      );
  }, [
    nowModalContent,
    confirmDateTime,
    confirmPlace,
    moimId,
    isPendingConfirmDateTime,
    isPendingConfirmPlacd,
  ]);

  const menuItems = useMemo(() => {
    if (role === 'MOIMER') {
      return (
        <ChatBottomMenu>
          <ChatMenuItem
            icon={<Picker />}
            description="장소 정하기"
            onClick={() => {
              setNowModalContent('place');
              setIsModalOpen(true);
            }}
          />
          <ChatMenuItem
            icon={<CalenderClock />}
            description="날짜/시간 정하기"
            onClick={() => {
              setNowModalContent('datetime');
              setIsModalOpen(true);
            }}
          />
        </ChatBottomMenu>
      );
    }
    return (
      <MissingFallback
        text={'모임에 참여한 사람은 \n 아직 할 수 있는 기능이 없어요'}
      />
    );
  }, [role]);

  return (
    <ChattingRoomLayout>
      <ChattingRoomLayout.Header>
        <ChattingRoomLayout.Header.Left>
          <SolidArrow direction="left" onClick={() => navigate(-1)} />
        </ChattingRoomLayout.Header.Left>
        <ChattingRoomLayout.Header.Center>
          <DarakbangNameWrapper font={theme.typography.s1}>
            {moim?.title}
          </DarakbangNameWrapper>
          <span
            css={[
              css`
                margin: 1px 0 0 2px;
              `,
              theme.coloredTypography.s1(theme.colorPalette.grey[400]),
            ]}
          >
            {' ' + moim?.currentPeople}
          </span>
        </ChattingRoomLayout.Header.Center>
        <ChattingRoomLayout.Header.Right>
          <Hamburger onClick={() => setIsSidebarOpen(true)} />
        </ChattingRoomLayout.Header.Right>
      </ChattingRoomLayout.Header>
      <ChattingRoomSidebar
        members={participants || []}
        isOpen={isSidebarOpen}
        onClose={() => setIsSidebarOpen(false)}
      />
      <ChattingRoomLayout.HeaderBottom>
        <MoimInfoAccordion
          status={moim?.status || 'MOIMING'}
          date={moim?.date}
          time={moim?.time}
          place={moim?.place}
        />
      </ChattingRoomLayout.HeaderBottom>
      <ChatList chats={chats} />
      <ChattingRoomLayout.Footer>
        <ChattingFooter
          onSubmit={handleSendMessage}
          disabled={isPendingSendMessage}
          onMenuClick={() => setIsMenuOpen(!isMenuOpen)}
          onTextAreaFocus={() => {
            setIsMenuOpen(false);
          }}
        />
        {isMenuOpen && menuItems}
      </ChattingRoomLayout.Footer>

      {isModalOpen && modal}
    </ChattingRoomLayout>
  );
}
