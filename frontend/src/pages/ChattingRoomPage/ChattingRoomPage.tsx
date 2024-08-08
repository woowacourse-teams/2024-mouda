import { useMemo, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';

import Back from '@_common/assets/back.svg';
import CalenderClock from '@_components/Icons/CalenderClock';
import ChatBottomMenu from '@_components/ChatBottomMenu/ChatBottomMenu';
import ChatList from '@_components/ChatList/ChatList';
import ChatMenuItem from '@_components/ChatMenuItem/ChatMenuItem';
import ChattingFooter from '@_components/ChattingFooter/ChattingFooter';
import ChattingRoomLayout from '@_layouts/ChattingRoomLayout/ChattingRoomLayout';
import DateTimeModalContent from '@_components/DateTimeModalContent/DateTimeModalContent';
import Modal from '@_components/Modal/Modal';
import Picker from '@_components/Icons/Picker';
import PlaceModalContent from '@_components/PlaceModalContent/PlaceModalContent';
import useChamyoMine from '@_hooks/queries/useChamyoMine';
import useChats from '@_hooks/queries/useChat';
import useConfirmDateTime from '@_hooks/mutaions/useConfirmDatetime';
import useConfirmPlace from '@_hooks/mutaions/useConfirmPlace';
import useMoims from '@_hooks/queries/useMoims';
import useSendMessage from '@_hooks/mutaions/useSendMessage';
import { useTheme } from '@emotion/react';

type ModalContent = 'place' | 'datetime';

export default function ChattingRoomPage() {
  const theme = useTheme();
  const params = useParams();
  const navigate = useNavigate();

  const moimId = +(params.moimId || '0');

  const { moims } = useMoims();
  const { chats } = useChats(moimId);
  const { role } = useChamyoMine(moimId);

  const { mutate: confirmDateTime } = useConfirmDateTime();
  const { mutate: confirmPlace } = useConfirmPlace();
  const { mutate: handleSendMessage } = useSendMessage(moimId);

  const [isMenuOpen, setIsMenuOpen] = useState(false);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [nowModalContent, setNowModalContent] = useState<ModalContent>('place');

  const moimTitle = useMemo(
    () => moims?.find((moim) => moim.moimId === moimId)?.title,
    [moims, moimId],
  );

  const modal = useMemo(() => {
    if (nowModalContent === 'datetime')
      return (
        <Modal onClose={() => setIsModalOpen(false)}>
          <DateTimeModalContent
            onCancel={() => setIsModalOpen(false)}
            onConfirm={({ date, time }: { date: string; time: string }) => {
              confirmDateTime({ moimId, date, time });
              setIsModalOpen(false);
            }}
          />
        </Modal>
      );
    if (nowModalContent === 'place')
      return (
        <Modal onClose={() => setIsModalOpen(false)}>
          <PlaceModalContent
            onCancel={() => setIsModalOpen(false)}
            onConfirm={(place: string) => {
              confirmPlace({ moimId, place });
              setIsModalOpen(false);
            }}
          />
        </Modal>
      );
  }, [nowModalContent, confirmDateTime, confirmPlace, moimId]);

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
    return <ChatBottomMenu />;
  }, [role]);

  return (
    <ChattingRoomLayout>
      <ChattingRoomLayout.Header>
        <ChattingRoomLayout.Header.Left>
          <div onClick={() => navigate(-1)}>
            <Back />
          </div>
        </ChattingRoomLayout.Header.Left>
        <ChattingRoomLayout.Header.Center>
          <h2 css={theme.typography.s1}>{moimTitle}</h2>
        </ChattingRoomLayout.Header.Center>
      </ChattingRoomLayout.Header>
      <ChatList chats={chats} />
      <ChattingRoomLayout.Footer>
        <ChattingFooter
          onSubmit={handleSendMessage}
          onMenuClick={() => setIsMenuOpen(!isMenuOpen)}
        />
        {isMenuOpen && menuItems}
      </ChattingRoomLayout.Footer>

      {isModalOpen && modal}
    </ChattingRoomLayout>
  );
}
